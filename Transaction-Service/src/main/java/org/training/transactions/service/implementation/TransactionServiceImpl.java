package org.training.transactions.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.training.transactions.exception.AccountStatusException;
import org.training.transactions.exception.GlobalErrorCode;
import org.training.transactions.exception.InsufficientBalance;
import org.training.transactions.exception.ResourceNotFound;

import org.training.transactions.external.AccountService;
import org.training.transactions.model.TransactionStatus;
import org.training.transactions.model.TransactionType;
import org.training.transactions.model.dto.TransactionDto;
import org.training.transactions.model.entity.Transaction;
import org.training.transactions.model.external.Account;
import org.training.transactions.model.mapper.TransactionMapper;
import org.training.transactions.model.response.Response;
import org.training.transactions.model.response.TransactionRequest;
import org.training.transactions.repository.TransactionRepository;
import org.training.transactions.service.TransactionService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final TransactionMapper transactionMapper = new TransactionMapper();

    @Value("${spring.application.ok}")
    private String ok;

    // Explicit constructor to initialize final fields
    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    @Override
    public Response addTransaction(TransactionDto transactionDto) {
        ResponseEntity<Account> response = accountService.readByAccountNumber(transactionDto.getAccountId());

        if (Objects.isNull(response.getBody())) {
            throw new ResourceNotFound("Requested account not found on the server", GlobalErrorCode.NOT_FOUND);
        }

        Account account = response.getBody();
        Transaction transaction = transactionMapper.convertToEntity(transactionDto);

        if (TransactionType.DEPOSIT.toString().equals(transactionDto.getTransactionType())) {
            account.setAvailableBalance(account.getAvailableBalance().add(transactionDto.getAmount()));
        } else if (TransactionType.WITHDRAWAL.toString().equals(transactionDto.getTransactionType())) {
            if (!"ACTIVE".equals(account.getAccountStatus())) {
                log.error("account is either inactive/closed, cannot process the transaction");
                throw new AccountStatusException("account is inactive or closed");
            }

            if (account.getAvailableBalance().compareTo(transactionDto.getAmount()) < 0) {
                log.error("insufficient balance in the account");
                throw new InsufficientBalance("Insufficient balance in the account");
            }

            transaction.setAmount(transactionDto.getAmount().negate());
            account.setAvailableBalance(account.getAvailableBalance().subtract(transactionDto.getAmount()));
        }

        transaction.setTransactionType(TransactionType.valueOf(transactionDto.getTransactionType()));
        transaction.setComments(transactionDto.getDescription());
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setReferenceId(UUID.randomUUID().toString());

        accountService.updateAccount(transactionDto.getAccountId(), account);
        transactionRepository.save(transaction);

        return Response.builder()
                .message("Transaction completed successfully")
                .responseCode(ok)
                .build();
    }

    @Override
    public Response internalTransaction(List<TransactionDto> transactionDtos, String transactionReference) {
        List<Transaction> transactions = transactionMapper.convertToEntityList(transactionDtos);

        transactions.forEach(transaction -> {
            transaction.setTransactionType(TransactionType.INTERNAL_TRANSFER);
            transaction.setStatus(TransactionStatus.COMPLETED);
            transaction.setReferenceId(transactionReference);
        });

        transactionRepository.saveAll(transactions);

        return Response.builder()
                .responseCode(ok)
                .message("Transaction completed successfully")
                .build();
    }

    @Override
    public List<TransactionRequest> getTransaction(String accountId) {
        return transactionRepository.findTransactionByAccountId(accountId)
                .stream()
                .map(transaction -> {
                    TransactionRequest transactionRequest = new TransactionRequest();
                    BeanUtils.copyProperties(transaction, transactionRequest);
                    transactionRequest.setTransactionStatus(transaction.getStatus().toString());
                    transactionRequest.setLocalDateTime(transaction.getTransactionDate());
                    transactionRequest.setTransactionType(transaction.getTransactionType().toString());
                    return transactionRequest;
                }).collect(Collectors.toList());
    }

    @Override
    public List<TransactionRequest> getTransactionByTransactionReference(String transactionReference) {
        return transactionRepository.findTransactionByReferenceId(transactionReference)
                .stream()
                .map(transaction -> {
                    TransactionRequest transactionRequest = new TransactionRequest();
                    BeanUtils.copyProperties(transaction, transactionRequest);
                    transactionRequest.setTransactionStatus(transaction.getStatus().toString());
                    transactionRequest.setLocalDateTime(transaction.getTransactionDate());
                    transactionRequest.setTransactionType(transaction.getTransactionType().toString());
                    return transactionRequest;
                }).collect(Collectors.toList());
    }
}
