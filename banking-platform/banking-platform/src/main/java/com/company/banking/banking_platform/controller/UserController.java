package com.company.banking.banking_platform.controller;


import com.company.banking.banking_platform.dto.CreateUserRequest;
import com.company.banking.banking_platform.entity.User;
import com.company.banking.banking_platform.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public User createUser(@Valid @RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }
}
