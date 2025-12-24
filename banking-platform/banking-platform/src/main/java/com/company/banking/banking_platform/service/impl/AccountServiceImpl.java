package com.company.banking.banking_platform.service.impl;



import com.company.banking.banking_platform.dto.*;
import com.company.banking.banking_platform.entity.Account;
import com.company.banking.banking_platform.entity.User;
import com.company.banking.banking_platform.exception.ResourceNotFoundException;
import com.company.banking.banking_platform.repository.AccountRepository;
import com.company.banking.banking_platform.repository.UserRepository;
import com.company.banking.banking_platform.service.AccountService;
import com.company.banking.banking_platform.service.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
@Autowired
    private  AccountRepository accountRepository;
@Autowired
    private  UserRepository userRepository;
@Autowired
    private PasswordEncoder passwordEncoder;


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
     Account account=new Account();
        if (SecurityUtil.isAdmin()) {
            account= accountRepository.findById(accountId)
                    .orElseThrow(() -> new RuntimeException("Account not found"));
        }
        String username = SecurityUtil.getCurrentUsername();
        account=accountRepository.findByIdAndOwnerUsername(accountId, username)
                .orElseThrow(() -> new AccessDeniedException("Unauthorized access"));
        AccountSummaryResponse accountSummary = AccountSummaryResponse.builder()
                .accountNumber(account.getAccountNumber().toString())
                .balance(account.getBalance())
                .accountType(account.getAccountType().name())
                .build();

        return accountSummary;
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();

    }

    public boolean registerUser(AuthRequest req) {

        if (userRepository.existsByUsername(req.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setEmail(req.getEmail());
        user.setMobile(req.getMobile());
        user.setRole(req.getRole());

        userRepository.save(user);
        return true;
    }



}

