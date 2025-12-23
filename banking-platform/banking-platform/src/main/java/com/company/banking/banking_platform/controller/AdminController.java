package com.company.banking.banking_platform.controller;

import com.company.banking.banking_platform.entity.Account;
import com.company.banking.banking_platform.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AccountService accountService;

    @GetMapping("/accounts")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Account> getAllAccounts() {
        return accountService.findAll();
    }
}
