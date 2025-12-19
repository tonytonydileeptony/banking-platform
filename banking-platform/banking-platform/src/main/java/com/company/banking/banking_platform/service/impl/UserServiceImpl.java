package com.company.banking.banking_platform.service.impl;



import com.company.banking.banking_platform.dto.CreateUserRequest;
import com.company.banking.banking_platform.entity.User;
import com.company.banking.banking_platform.exception.BadRequestException;
import com.company.banking.banking_platform.repository.UserRepository;
import com.company.banking.banking_platform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(CreateUserRequest request) {

        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new BadRequestException("Email already exists");
                });

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .mobile(request.getMobile())
                .build();

        return userRepository.save(user);
    }
}

