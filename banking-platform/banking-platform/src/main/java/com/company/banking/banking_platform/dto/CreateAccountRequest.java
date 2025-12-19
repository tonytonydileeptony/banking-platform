package com.company.banking.banking_platform.dto;


import com.company.banking.banking_platform.entity.AccountType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateAccountRequest {

    @NotNull
    private Long userId;

    @NotNull
    private AccountType accountType;

    @NotNull
    private BigDecimal initialBalance;
}
