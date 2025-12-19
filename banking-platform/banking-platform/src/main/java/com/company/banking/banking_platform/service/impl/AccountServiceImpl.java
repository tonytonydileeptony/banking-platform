package com.company.banking.banking_platform.service.impl;



import com.company.banking.banking_platform.dto.*;
import com.company.banking.banking_platform.entity.Account;
import com.company.banking.banking_platform.entity.User;
import com.company.banking.banking_platform.exception.ResourceNotFoundException;
import com.company.banking.banking_platform.repository.AccountRepository;
import com.company.banking.banking_platform.repository.UserRepository;
import com.company.banking.banking_platform.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Account createAccount(CreateAccountRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Account account = Account.builder()
                .accountNumber(UUID.randomUUID().toString())
                .accountType(request.getAccountType())
                .balance(request.getInitialBalance())
                .user(user)
                .build();

        return accountRepository.save(account);
    }
    @Override
    public AccountSummaryResponse getAccountSummary(Long accountId) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        return AccountSummaryResponse.builder()
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .accountType(account.getAccountType().name())
                .build();
    }
}

