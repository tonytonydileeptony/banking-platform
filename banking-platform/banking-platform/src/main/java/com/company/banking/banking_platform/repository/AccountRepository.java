package com.company.banking.banking_platform.repository;



import com.company.banking.banking_platform.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

    Optional<Account> findByIdAndOwnerUsername(Long accountId, String username);
}
