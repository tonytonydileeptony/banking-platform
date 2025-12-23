package com.company.banking.banking_platform.service.impl;



import com.company.banking.banking_platform.dto.*;
import com.company.banking.banking_platform.entity.Account;
import com.company.banking.banking_platform.entity.User;
import com.company.banking.banking_platform.exception.ResourceNotFoundException;
import com.company.banking.banking_platform.repository.AccountRepository;
import com.company.banking.banking_platform.repository.UserRepository;
import com.company.banking.banking_platform.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
@Autowired
    private  AccountRepository accountRepository;
@Autowired
    private  UserRepository userRepository;


    @Override
    @Transactional
    public Account createAccount(CreateAccountRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Account account = new Account();
        account.setAccountNumber(UUID.randomUUID().toString());
        account.setAccountType(request.getAccountType());
        account.setBalance(request.getInitialBalance());
        account.setUser(user);

        return accountRepository.save(account);
    }
    @Override
    public AccountSummaryResponse getAccountSummary(Long accountId) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        AccountSummaryResponse accountSummary = AccountSummaryResponse.builder()
                .accountNumber(account.getAccountNumber().toString())
                .balance(account.getBalance())
                .accountType(account.getAccountType().name())
                .build();

        return accountSummary;
    }

    @Override
    public boolean registerUser(AuthRequest req) {
        User user= User.builder().username(req.getUsername())
                        .password(req.getPassword())
                .role(req.getRole()).email(req.getEmail()).mobile(req.getMobile()).build();

         userRepository.save(user);
         return true;
    }
}

