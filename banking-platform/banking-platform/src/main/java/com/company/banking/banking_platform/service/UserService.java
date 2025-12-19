package com.company.banking.banking_platform.service;



import com.company.banking.banking_platform.dto.CreateUserRequest;
import com.company.banking.banking_platform.entity.User;

public interface UserService {

    User createUser(CreateUserRequest request);
}

