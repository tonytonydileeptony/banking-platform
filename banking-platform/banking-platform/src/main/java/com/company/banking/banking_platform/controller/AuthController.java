package com.company.banking.banking_platform.controller;

import com.company.banking.banking_platform.config.JwtUtil;
import com.company.banking.banking_platform.dto.AuthRequest;
import com.company.banking.banking_platform.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final AccountService accountService;

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        return jwtUtil.generateToken(request.getUsername());
    }

    @PostMapping("/register")
    public boolean register(@Valid @RequestBody AuthRequest req) {
        return accountService.registerUser(req);
    }
}

