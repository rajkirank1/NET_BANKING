package org.training.fundtransfer.service.implementation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.training.fundtransfer.exception.AccountUpdateException;
import org.training.fundtransfer.exception.GlobalErrorCode;
import org.training.fundtransfer.exception.InsufficientBalance;
import org.training.fundtransfer.exception.ResourceNotFound;
import org.training.fundtransfer.external.AccountService;
import org.training.fundtransfer.external.TransactionService;
import org.training.fundtransfer.model.mapper.FundTransferMapper;
import org.training.fundtransfer.model.TransactionStatus;
import org.training.fundtransfer.model.TransferType;
import org.training.fundtransfer.model.dto.Account;
import org.training.fundtransfer.model.dto.FundTransferDto;
import org.training.fundtransfer.model.dto.Transaction;
import org.training.fundtransfer.model.dto.request.FundTransferRequest;
import org.training.fundtransfer.model.dto.response.FundTransferResponse;
import org.training.fundtransfer.model.entity.FundTransfer;
import org.training.fundtransfer.repository.FundTransferRepository;
import org.training.fundtransfer.service.FundTransferService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class FundTransferServiceImpl implements FundTransferService {

    private final AccountService accountService;
    private final FundTransferRepository fundTransferRepository;
    private final TransactionService transactionService;

    @Value("${spring.application.ok}")
    private String ok;

    private final FundTransferMapper fundTransferMapper = new FundTransferMapper();

    // Explicit constructor to initialize final fields
    public FundTransferServiceImpl(AccountService accountService,
                                   FundTransferRepository fundTransferRepository,
                                   TransactionService transactionService) {
        this.accountService = accountService;
        this.fundTransferRepository = fundTransferRepository;
        this.transactionService = transactionService;
    }

    @Override
    public FundTransferResponse fundTransfer(FundTransferRequest fundTransferRequest) {

        ResponseEntity<Account> response = accountService.readByAccountNumber(fundTransferRequest.getFromAccount());
        Account fromAccount = response.getBody();

        if (Objects.isNull(fromAccount)) {
            throw new ResourceNotFound("Requested account not found on the server", GlobalErrorCode.NOT_FOUND);
        }

        if (!"ACTIVE".equals(fromAccount.getAccountStatus())) {
            throw new AccountUpdateException("Account status is not ACTIVE", GlobalErrorCode.NOT_ACCEPTABLE);
        }

        if (fromAccount.getAvailableBalance().compareTo(fundTransferRequest.getAmount()) < 0) {
            throw new InsufficientBalance("Requested amount is not available", GlobalErrorCode.NOT_ACCEPTABLE);
        }

        response = accountService.readByAccountNumber(fundTransferRequest.getToAccount());
        Account toAccount = response.getBody();

        if (Objects.isNull(toAccount)) {
            throw new ResourceNotFound("Requested account not found on the server", GlobalErrorCode.NOT_FOUND);
        }

        String transactionId = internalTransfer(fromAccount, toAccount, fundTransferRequest.getAmount());

        FundTransfer fundTransfer = FundTransfer.builder()
                .transferType(TransferType.INTERNAL)
                .amount(fundTransferRequest.getAmount())
                .fromAccount(fromAccount.getAccountNumber())
                .transactionReference(transactionId)
                .status(TransactionStatus.SUCCESS)
                .toAccount(toAccount.getAccountNumber())
                .build();

        fundTransferRepository.save(fundTransfer);

        return FundTransferResponse.builder()
                .transactionId(transactionId)
                .message("Fund transfer was successful")
                .build();
    }

    private String internalTransfer(Account fromAccount, Account toAccount, BigDecimal amount) {
        fromAccount.setAvailableBalance(fromAccount.getAvailableBalance().subtract(amount));
        accountService.updateAccount(fromAccount.getAccountNumber(), fromAccount);

        toAccount.setAvailableBalance(toAccount.getAvailableBalance().add(amount));
        accountService.updateAccount(toAccount.getAccountNumber(), toAccount);

        List<Transaction> transactions = List.of(
                Transaction.builder()
                        .accountId(fromAccount.getAccountNumber())
                        .transactionType("INTERNAL_TRANSFER")
                        .amount(amount.negate())
                        .description("Internal fund transfer from " + fromAccount.getAccountNumber() + " to " + toAccount.getAccountNumber())
                        .build(),

                Transaction.builder()
                        .accountId(toAccount.getAccountNumber())
                        .transactionType("INTERNAL_TRANSFER")
                        .amount(amount)
                        .description("Internal fund transfer received from: " + fromAccount.getAccountNumber())
                        .build()
        );

        String transactionReference = UUID.randomUUID().toString();
        transactionService.makeInternalTransactions(transactions, transactionReference);

        return transactionReference;
    }

    @Override
    public FundTransferDto getTransferDetailsFromReferenceId(String referenceId) {
        return fundTransferRepository.findFundTransferByTransactionReference(referenceId)
                .map(fundTransferMapper::convertToDto)
                .orElseThrow(() -> new ResourceNotFound("Fund transfer not found", GlobalErrorCode.NOT_FOUND));
    }

    @Override
    public List<FundTransferDto> getAllTransfersByAccountId(String accountId) {
        return fundTransferMapper.convertToDtoList(fundTransferRepository.findFundTransferByFromAccount(accountId));
    }
}
