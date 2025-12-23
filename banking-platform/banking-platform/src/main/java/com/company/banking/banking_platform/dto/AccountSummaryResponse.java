package com.company.banking.banking_platform.dto;



import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AccountSummaryResponse {

    private String accountNumber;
    private BigDecimal balance;
    private String accountType;

}

