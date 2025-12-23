package com.company.banking.banking_platform.controller;



import com.company.banking.banking_platform.config.JwtUtil;
import com.company.banking.banking_platform.dto.AccountResponse;
import com.company.banking.banking_platform.dto.AccountSummaryResponse;
import com.company.banking.banking_platform.dto.AuthRequest;
import com.company.banking.banking_platform.dto.CreateAccountRequest;
import com.company.banking.banking_platform.entity.Account;
import com.company.banking.banking_platform.entity.Role;
import com.company.banking.banking_platform.entity.User;
import com.company.banking.banking_platform.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AccountController {
 @Autowired
    private  AccountService accountService;



    @PostMapping
    public AccountResponse createAccount(@Valid @RequestBody CreateAccountRequest request) {
        Account account = accountService.createAccount(request);

         return AccountResponse.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType().name())
                .balance(account.getBalance())
                .build();


    }
    @GetMapping("/{accountId}/summary")
    public AccountSummaryResponse getSummary(@PathVariable Long accountId) {
        return accountService.getAccountSummary(accountId);
    }
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword())
        );
        return jwtUtil.generateToken(request.getUsername());
    }
    @PostMapping("/register")
    public boolean register(@Valid @RequestBody AuthRequest req) {
      return accountService.registerUser(req);
    }
}

