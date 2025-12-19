package com.company.banking.banking_platform.controller;



import com.company.banking.banking_platform.dto.TransactionRequest;
import com.company.banking.banking_platform.dto.TransactionResponse;
import com.company.banking.banking_platform.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public String deposit(@Valid @RequestBody TransactionRequest request) {
        transactionService.deposit(request);
        return "Deposit successful";
    }

    @PostMapping("/withdraw")
    public String withdraw(@Valid @RequestBody TransactionRequest request) {
        transactionService.withdraw(request);
        return "Withdrawal successful";
    }
    @GetMapping("/history/{accountId}")
    public Page<TransactionResponse> getHistory(
            @PathVariable Long accountId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy) {

        return transactionService.getTransactionHistory(accountId, page, size, sortBy);
    }

}

