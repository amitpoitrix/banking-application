package com.example.basic.demo.model;

import java.math.BigDecimal;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private final String accountId;
    private BigDecimal balance;
    private final ReentrantLock lock = new ReentrantLock();

    public Account(String accountId, BigDecimal initialBalance) {
        this.accountId = accountId;
        this.balance = initialBalance;
    }

    public String getAccountId() {
        return accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public ReentrantLock getLock() {
        return lock;
    }
}
