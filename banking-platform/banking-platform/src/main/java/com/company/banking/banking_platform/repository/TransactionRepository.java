package com.company.banking.banking_platform.repository;



import com.company.banking.banking_platform.entity.Transaction;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findByAccountId(Long accountId, Pageable pageable);
}

