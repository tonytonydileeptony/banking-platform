package com.company.banking.banking_platform.controller;



import com.company.banking.banking_platform.dto.TransactionRequest;
import com.company.banking.banking_platform.dto.TransactionResponse;
import com.company.banking.banking_platform.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private  TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<Void> deposit(@Valid @RequestBody TransactionRequest request) {
        transactionService.deposit(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdraw(@Valid @RequestBody TransactionRequest request) {
        transactionService.withdraw(request);
        return ResponseEntity.ok().build();
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

