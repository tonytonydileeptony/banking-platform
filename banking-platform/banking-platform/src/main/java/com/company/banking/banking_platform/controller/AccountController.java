package com.company.banking.banking_platform.controller;


import com.company.banking.banking_platform.dto.AccountResponse;
import com.company.banking.banking_platform.dto.AccountSummaryResponse;
import com.company.banking.banking_platform.dto.CreateAccountRequest;
import com.company.banking.banking_platform.entity.Account;
import com.company.banking.banking_platform.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AccountResponse createAccount(
            @Valid @RequestBody CreateAccountRequest request) {

        Account account = accountService.createAccount(request);

        return AccountResponse.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType().name())
                .balance(account.getBalance())
                .build();
    }

    @GetMapping("/{accountId}/summary")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AccountSummaryResponse getSummary(@PathVariable Long accountId) {
        return accountService.getAccountSummary(accountId);
    }

    @GetMapping("/balance")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String getBalance() {
        return "Your account balance is ₹50,000";
    }

    @PostMapping("/transfer")
    @PreAuthorize("hasRole('USER')")
    public String transferMoney() {
        return "Money transferred successfully";
    }
}
