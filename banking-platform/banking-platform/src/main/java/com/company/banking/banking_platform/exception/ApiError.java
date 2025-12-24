package com.company.banking.banking_platform.exception;



import java.time.LocalDateTime;

public class ApiError {

    private String path;
    private String message;
    private int status;
    private LocalDateTime timestamp;

    public ApiError(String path, String message, int status) {
        this.path = path;
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    // getters
}

