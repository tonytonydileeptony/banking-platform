package com.company.banking.banking_platform.service;



import com.company.banking.banking_platform.dto.AccountSummaryResponse;
import com.company.banking.banking_platform.dto.CreateAccountRequest;
import com.company.banking.banking_platform.entity.Account;

public interface AccountService {

    Account createAccount(CreateAccountRequest request);
    AccountSummaryResponse getAccountSummary(Long accountId);
}

