package com.company.banking.banking_platform.service.impl;



import com.company.banking.banking_platform.dto.AuthRequest;
import com.company.banking.banking_platform.dto.CreateUserRequest;
import com.company.banking.banking_platform.entity.User;
import com.company.banking.banking_platform.dto.UserResponse;
import com.company.banking.banking_platform.exception.BadRequestException;
import com.company.banking.banking_platform.exception.ResourceNotFoundException;
import com.company.banking.banking_platform.repository.UserRepository;
import com.company.banking.banking_platform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private  UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    @Override
    public User createUser(CreateUserRequest request) {

        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new BadRequestException("Email already exists");
                });

        User user = new User();
        user.setUsername(request.getFullName());
        user.setEmail(request.getEmail());
        user.setMobile(request.getMobile());


        return userRepository.save(user);
    }
    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return mapToResponse(user);
    }

    @Override
    public Page<UserResponse> getAllUsers(int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        return userRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .build();
    }

    public boolean registerUser(AuthRequest req) {

        if (userRepository.existsByUsername(req.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setEmail(req.getEmail());
        user.setMobile(req.getMobile());
        user.setRole(req.getRole());

        userRepository.save(user);
        return true;
    }
}

