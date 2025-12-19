package com.company.banking.banking_platform.service.impl;



import com.company.banking.banking_platform.dto.TransactionRequest;
import com.company.banking.banking_platform.dto.TransactionResponse;
import com.company.banking.banking_platform.entity.*;
import com.company.banking.banking_platform.exception.BadRequestException;
import com.company.banking.banking_platform.exception.ResourceNotFoundException;
import com.company.banking.banking_platform.repository.AccountRepository;
import com.company.banking.banking_platform.repository.TransactionRepository;
import com.company.banking.banking_platform.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public void deposit(TransactionRequest request) {

        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        account.setBalance(account.getBalance().add(request.getAmount()));

        saveTransaction(account, request.getAmount(), TransactionType.DEPOSIT);
    }

    @Override
    @Transactional
    public void withdraw(TransactionRequest request) {

        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new BadRequestException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(request.getAmount()));

        saveTransaction(account, request.getAmount(), TransactionType.WITHDRAW);
    }

    private void saveTransaction(Account account, BigDecimal amount, TransactionType type) {

        Transaction transaction = Transaction.builder()
                .referenceId(UUID.randomUUID().toString())
                .account(account)
                .transactionType(type)
                .amount(amount)
                .balanceAfter(account.getBalance())
                .createdAt(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);
    }
    @Override
    public Page<TransactionResponse> getTransactionHistory(
            Long accountId, int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());

        return transactionRepository.findByAccountId(accountId, pageable)
                .map(tx -> TransactionResponse.builder()
                        .referenceId(tx.getReferenceId())
                        .transactionType(tx.getTransactionType())
                        .amount(tx.getAmount())
                        .balanceAfter(tx.getBalanceAfter())
                        .createdAt(tx.getCreatedAt())
                        .build()
                );}
}

