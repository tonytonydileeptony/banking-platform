package com.company.banking.banking_platform.service;



import com.company.banking.banking_platform.dto.AuthRequest;
import com.company.banking.banking_platform.dto.CreateUserRequest;
import com.company.banking.banking_platform.dto.UserResponse;
import com.company.banking.banking_platform.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {

    User createUser(CreateUserRequest request);
    UserResponse getUserById(Long id);
    boolean registerUser(AuthRequest request);

    Page<UserResponse> getAllUsers(int page, int size, String sortBy);
}

