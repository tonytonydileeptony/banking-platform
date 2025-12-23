package com.company.banking.banking_platform.repository;



import com.company.banking.banking_platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}

