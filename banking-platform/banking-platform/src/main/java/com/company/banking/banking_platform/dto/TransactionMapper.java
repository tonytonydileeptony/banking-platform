package com.company.banking.banking_platform.dto;

import com.company.banking.banking_platform.entity.Transaction;

public class TransactionMapper {

    public static TransactionResponse toResponse(Transaction tx) {
        TransactionResponse dto = new TransactionResponse();
        dto.setReferenceId(tx.getReferenceId());
        dto.setTransactionType(tx.getTransactionType());
        dto.setAmount(tx.getAmount());
        dto.setBalanceAfter(tx.getBalanceAfter());
        dto.setCreatedAt(tx.getCreatedAt());
        return dto;
    }
}

