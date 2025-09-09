package org.training.user.service.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.training.user.service.exception.EmptyFields;
import org.training.user.service.exception.ResourceConflictException;
import org.training.user.service.exception.ResourceNotFound;

import org.training.user.service.external.AccountService;
import org.training.user.service.model.Status;
import org.training.user.service.model.dto.CreateUser;
import org.training.user.service.model.dto.UserDto;
import org.training.user.service.model.dto.UserUpdate;
import org.training.user.service.model.dto.UserUpdateStatus;
import org.training.user.service.model.dto.response.Response;
import org.training.user.service.model.entity.User;
import org.training.user.service.model.entity.UserProfile;
import org.training.user.service.model.external.Account;
import org.training.user.service.model.mapper.UserMapper;
import org.training.user.service.repository.UserRepository;
import org.training.user.service.service.KeycloakService;
import org.training.user.service.service.UserService;
import org.training.user.service.utils.FieldChecker;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Objects;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final KeycloakService keycloakService;
    private final AccountService accountService;
    private final UserMapper userMapper = new UserMapper();

    @Value("${spring.application.success}")
    private String responseCodeSuccess;

    @Value("${spring.application.not_found}")
    private String responseCodeNotFound;

    public UserServiceImpl(UserRepository userRepository,
                           KeycloakService keycloakService,
                           AccountService accountService) {
        this.userRepository = userRepository;
        this.keycloakService = keycloakService;
        this.accountService = accountService;
    }

    @Override
    public Response createUser(CreateUser userDto) {
        List<UserRepresentation> userRepresentations = keycloakService.readUserByEmail(userDto.getEmailId());
        if (!userRepresentations.isEmpty()) {
            log.error("This emailId is already registered as a user");
            throw new ResourceConflictException("This emailId is already registered as a user");
        }

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(userDto.getEmailId());
        userRepresentation.setFirstName(userDto.getFirstName());
        userRepresentation.setLastName(userDto.getLastName());
        userRepresentation.setEmailVerified(false);
        userRepresentation.setEnabled(false);
        userRepresentation.setEmail(userDto.getEmailId());

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(userDto.getPassword());
        credentialRepresentation.setTemporary(false);

        userRepresentation.setCredentials(List.of(credentialRepresentation));

        Integer userCreationResponse = keycloakService.createUser(userRepresentation);

        if (userCreationResponse.equals(201)) {
            List<UserRepresentation> representations = keycloakService.readUserByEmail(userDto.getEmailId());

            if (representations.isEmpty()) {
                log.error("User was created but cannot be retrieved from Keycloak");
                throw new RuntimeException("User creation failed during retrieval");
            }

            UserProfile userProfile = new UserProfile();
            userProfile.setFirstName(userDto.getFirstName());
            userProfile.setLastName(userDto.getLastName());

            User user = new User();
            user.setEmailId(userDto.getEmailId());
            user.setContactNo(userDto.getContactNumber());
            user.setStatus(Status.PENDING);
            user.setUserProfile(userProfile);
            user.setAuthId(representations.get(0).getId());
            user.setIdentificationNumber(UUID.randomUUID().toString());

            userRepository.save(user);

            return Response.builder()
                    .responseMessage("User created successfully")
                    .responseCode(responseCodeSuccess)
                    .build();
        }

        throw new RuntimeException("User creation failed");
    }


    @Override
    public List<UserDto> readAllUsers() {
        List<User> users = userRepository.findAll();

        Map<String, UserRepresentation> userRepresentationMap = keycloakService.readUsers(
                users.stream()
                        .map(User::getAuthId)
                        .collect(Collectors.toList())
        ).stream().collect(Collectors.toMap(UserRepresentation::getId, Function.identity()));

        return users.stream()
                .map(user -> {
                    UserDto userDto = userMapper.convertToDto(user);
                    UserRepresentation userRepresentation = userRepresentationMap.get(user.getAuthId());
                    if (userRepresentation != null) {
                        userDto.setEmailId(userRepresentation.getEmail());
                    }
                    userDto.setUserId(user.getUserId());
                    userDto.setIdentificationNumber(user.getIdentificationNumber());
                    return userDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserDto readUser(String authId) {
        User user = userRepository.findUserByAuthId(authId)
                .orElseThrow(() -> new ResourceNotFound("User not found on the server"));

        UserRepresentation userRepresentation = keycloakService.readUser(authId);

        UserDto userDto = userMapper.convertToDto(user);
        if (userRepresentation != null) {
            userDto.setEmailId(userRepresentation.getEmail());
        }

        return userDto;
    }

    @Override
    public Response updateUserStatus(Long id, UserUpdateStatus userUpdate) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("User not found on the server"));

        if (FieldChecker.hasEmptyFields(user)) {
            log.error("User is not updated completely");
            throw new EmptyFields("Please update the user completely", responseCodeNotFound);
        }

        if (userUpdate.getStatus().equals(Status.APPROVED)) {
            UserRepresentation userRepresentation = keycloakService.readUser(user.getAuthId());
            userRepresentation.setEnabled(true);
            userRepresentation.setEmailVerified(true);
            keycloakService.updateUser(userRepresentation);
        }

        user.setStatus(userUpdate.getStatus());
        userRepository.save(user);

        return Response.builder()
                .responseMessage("User updated successfully")
                .responseCode(responseCodeSuccess)
                .build();
    }

    @Override
    public UserDto readUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User not found on the server"));

        return userMapper.convertToDto(user);
    }

    @Override
    public Response updateUser(Long id, UserUpdate userUpdate) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("User not found on the server"));

        user.setContactNo(userUpdate.getContactNo());
        BeanUtils.copyProperties(userUpdate, user.getUserProfile());

        userRepository.save(user);

        return Response.builder()
                .responseCode(responseCodeSuccess)
                .responseMessage("User updated successfully")
                .build();
    }

    @Override
    public UserDto readUserByAccountId(String accountId) {
        ResponseEntity<Account> response = accountService.readByAccountNumber(accountId);

        if (Objects.isNull(response.getBody())) {
            throw new ResourceNotFound("Account not found on the server");
        }

        Long userId = response.getBody().getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User not found on the server"));

        return userMapper.convertToDto(user);
    }
}
