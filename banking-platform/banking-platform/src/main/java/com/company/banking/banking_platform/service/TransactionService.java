package com.company.banking.banking_platform.service;



import com.company.banking.banking_platform.dto.TransactionRequest;
import com.company.banking.banking_platform.dto.TransactionResponse;
import org.springframework.data.domain.Page;

public interface TransactionService {

    void deposit(TransactionRequest request);

    void withdraw(TransactionRequest request);
    Page<TransactionResponse> getTransactionHistory(
            Long accountId, int page, int size, String sortBy);

}

