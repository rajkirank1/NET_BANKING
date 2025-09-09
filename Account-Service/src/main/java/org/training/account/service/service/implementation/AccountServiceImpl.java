package org.training.account.service.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.training.account.service.exception.*;
import org.training.account.service.external.SequenceService;
import org.training.account.service.external.TransactionService;
import org.training.account.service.external.UserService;
import org.training.account.service.model.AccountStatus;
import org.training.account.service.model.AccountType;
import org.training.account.service.model.dto.AccountDto;
import org.training.account.service.model.dto.AccountStatusUpdate;
import org.training.account.service.model.dto.external.UserDto;
import org.training.account.service.model.dto.response.Response;
import org.training.account.service.model.entity.Account;
import org.training.account.service.model.mapper.AccountMapper;
import org.training.account.service.model.dto.external.TransactionResponse;
import org.training.account.service.repository.AccountRepository;
import org.training.account.service.service.AccountService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static org.training.account.service.model.Constants.ACC_PREFIX;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final UserService userService;
    private final AccountRepository accountRepository;
    private final SequenceService sequenceService;
    private final TransactionService transactionService;

    private final AccountMapper accountMapper = new AccountMapper();

    @Value("${spring.application.ok}")
    private String success;

    // Constructor for dependency injection (replace @RequiredArgsConstructor)
    public AccountServiceImpl(UserService userService, AccountRepository accountRepository,
                              SequenceService sequenceService, TransactionService transactionService) {
        this.userService = userService;
        this.accountRepository = accountRepository;
        this.sequenceService = sequenceService;
        this.transactionService = transactionService;
    }

    @Override
    public Response createAccount(AccountDto accountDto) {
        ResponseEntity<UserDto> user = userService.readUserById(accountDto.getUserId());
        if (Objects.isNull(user.getBody())) {
            throw new ResourceNotFound("user not found on the server");
        }

        accountRepository.findAccountByUserIdAndAccountType(accountDto.getUserId(),
                AccountType.valueOf(accountDto.getAccountType()))
                .ifPresent(account -> {
                    log.error("Account already exists on the server");
                    throw new ResourceConflict("Account already exists on the server");
                });

        Account account = accountMapper.convertToEntity(accountDto);
        account.setAccountNumber(ACC_PREFIX + String.format("%07d", sequenceService.generateAccountNumber().getAccountNumber()));
        account.setAccountStatus(AccountStatus.PENDING);
        account.setAvailableBalance(BigDecimal.valueOf(0));
        account.setAccountType(AccountType.valueOf(accountDto.getAccountType()));
        accountRepository.save(account);

        return Response.builder()
                .responseCode(success)
                .message("Account created successfully")
                .build();
    }

    @Override
    public Response updateStatus(String accountNumber, AccountStatusUpdate accountUpdate) {
        return accountRepository.findAccountByAccountNumber(accountNumber)
                .map(account -> {
                    if (account.getAccountStatus().equals(AccountStatus.ACTIVE)) {
                        throw new AccountStatusException("Account is inactive/closed");
                    }
                    if (account.getAvailableBalance().compareTo(BigDecimal.ZERO) < 0
                            || account.getAvailableBalance().compareTo(BigDecimal.valueOf(1000)) < 0) {
                        throw new InSufficientFunds("Minimum balance of Rs.1000 is required");
                    }
                    account.setAccountStatus(accountUpdate.getAccountStatus());
                    accountRepository.save(account);

                    return Response.builder()
                            .message("Account updated successfully")
                            .responseCode(success)
                            .build();
                }).orElseThrow(() -> new ResourceNotFound("Account not on the server"));
    }

    @Override
    public AccountDto readAccountByAccountNumber(String accountNumber) {
        return accountRepository.findAccountByAccountNumber(accountNumber)
                .map(account -> {
                    AccountDto accountDto = accountMapper.convertToDto(account);
                    accountDto.setAccountType(account.getAccountType().toString());
                    accountDto.setAccountStatus(account.getAccountStatus().toString());
                    return accountDto;
                })
                .orElseThrow(ResourceNotFound::new);
    }

    @Override
    public Response updateAccount(String accountNumber, AccountDto accountDto) {
        return accountRepository.findAccountByAccountNumber(accountDto.getAccountNumber())
                .map(account -> {
                    BeanUtils.copyProperties(accountDto, account);
                    accountRepository.save(account);

                    return Response.builder()
                            .responseCode(success)
                            .message("Account updated successfully")
                            .build();
                }).orElseThrow(() -> new ResourceNotFound("Account not found on the server"));
    }

    @Override
    public String getBalance(String accountNumber) {
        return accountRepository.findAccountByAccountNumber(accountNumber)
                .map(account -> account.getAvailableBalance().toString())
                .orElseThrow(ResourceNotFound::new);
    }

    @Override
    public List<TransactionResponse> getTransactionsFromAccountId(String accountId) {
        return transactionService.getTransactionsFromAccountId(accountId);
    }

    @Override
    public Response closeAccount(String accountNumber) {
        return accountRepository.findAccountByAccountNumber(accountNumber)
                .map(account -> {
                    if (BigDecimal.valueOf(Double.parseDouble(getBalance(accountNumber))).compareTo(BigDecimal.ZERO) != 0) {
                        throw new AccountClosingException("Balance should be zero");
                    }
                    account.setAccountStatus(AccountStatus.CLOSED);
                    return Response.builder()
                            .message("Account closed successfully")
                            .responseCode(success)
                            .build();
                }).orElseThrow(ResourceNotFound::new);
    }

    @Override
    public AccountDto readAccountByUserId(Long userId) {
        return accountRepository.findAccountByUserId(userId)
                .map(account -> {
                    if (!account.getAccountStatus().equals(AccountStatus.ACTIVE)) {
                        throw new AccountStatusException("Account is inactive/closed");
                    }
                    AccountDto accountDto = accountMapper.convertToDto(account);
                    accountDto.setAccountStatus(account.getAccountStatus().toString());
                    accountDto.setAccountType(account.getAccountType().toString());
                    return accountDto;
                }).orElseThrow(ResourceNotFound::new);
    }
}
