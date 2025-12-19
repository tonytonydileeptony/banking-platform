package com.company.banking.banking_platform.dto;



import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {

    @NotNull
    private Long accountId;

    @NotNull
    private BigDecimal amount;
}

