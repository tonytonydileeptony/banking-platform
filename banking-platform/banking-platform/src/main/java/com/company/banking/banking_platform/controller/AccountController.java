package com.company.banking.banking_platform.controller;



import com.company.banking.banking_platform.dto.AccountResponse;
import com.company.banking.banking_platform.dto.AccountSummaryResponse;
import com.company.banking.banking_platform.dto.CreateAccountRequest;
import com.company.banking.banking_platform.entity.Account;
import com.company.banking.banking_platform.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class AccountController {

    private  AccountService accountService;



    @PostMapping
    public AccountResponse createAccount(@Valid @RequestBody CreateAccountRequest request) {
        Account account = accountService.createAccount(request);

         AccountResponse accountResponse=new AccountResponse();
        accountResponse.setId(account.getId());
        accountResponse.setAccountNumber(account.getAccountNumber());
        accountResponse.setAccountType(account.getAccountType().name());
        accountResponse.setBalance(account.getBalance());
               return accountResponse;
    }
    @GetMapping("/{accountId}/summary")
    public AccountSummaryResponse getSummary(@PathVariable Long accountId) {
        return accountService.getAccountSummary(accountId);
    }
}

