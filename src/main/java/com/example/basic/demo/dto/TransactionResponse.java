package com.example.basic.demo.dto;


import java.math.BigDecimal;
import java.time.Instant;

public class TransactionResponse {
    private boolean success;
    private String message;
    private BigDecimal balance;
    private Instant timestamp;

    public TransactionResponse(boolean success, String message, BigDecimal balance, Instant timestamp) {
        this.success = success;
        this.message = message;
        this.balance = balance;
        this.timestamp = timestamp;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
