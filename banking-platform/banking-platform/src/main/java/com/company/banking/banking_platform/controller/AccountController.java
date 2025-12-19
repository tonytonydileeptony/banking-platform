package com.company.banking.banking_platform.controller;



import com.company.banking.banking_platform.dto.CreateAccountRequest;
import com.company.banking.banking_platform.entity.Account;
import com.company.banking.banking_platform.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public Account createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return accountService.createAccount(request);
    }
}

