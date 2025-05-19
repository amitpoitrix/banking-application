package com.example.basic.demo.service;

import com.example.basic.demo.dto.TransactionResponse;
import com.example.basic.demo.model.Account;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TransactionService {
    private final Map<String, Account> accounts = new ConcurrentHashMap<>();

    public TransactionResponse createAccount(String accountId, BigDecimal initialBalance) {
        Account newAccount = new Account(accountId, initialBalance);
        // Here treating accountId as map key
        accounts.put(accountId, newAccount);

        String message = "Account created successful with Account Id: " + newAccount.getAccountId();
        return new TransactionResponse(true, message, newAccount.getBalance(), Instant.now());
    }

    public TransactionResponse getBalance(String accountId) {
        Account account = accounts.get(accountId);
        if(account == null) {
            throw new IllegalArgumentException("User not found");
        }

        return new TransactionResponse(true, "Your balance amount is:", account.getBalance(), Instant.now());
    }

    public TransactionResponse deposit(String accountId, BigDecimal amount) throws IllegalAccessException {
        Account account = accounts.get(accountId);
        if(account == null) {
            throw new IllegalArgumentException("User not found");
        }

        account.getLock().lock();
        try{
            account.setBalance(account.getBalance().add(amount));
        } finally {
            account.getLock().unlock();
        }

        String message = "Successfully deposited amount: " + amount + " at accountId: " + accountId;
        return new TransactionResponse(true, message, account.getBalance(), Instant.now());
    }

    public TransactionResponse withdraw(String accountId, BigDecimal amount) {
        Account account = accounts.get(accountId);
        if(account == null) {
            throw new IllegalArgumentException("User not found");
        }

        account.getLock().lock();
        try{
            if(account.getBalance().compareTo(amount) < 0) {
                throw new IllegalStateException("Insufficient Balance in Account");
            }
            // Before withdrawing we need to check for sufficient balance
            account.setBalance(account.getBalance().subtract(amount));
        }finally {
            account.getLock().unlock();
        }

        String message = "Amount withdrawn Successfully";
        return new TransactionResponse(true, message, account.getBalance(), Instant.now());
    }

    public TransactionResponse transfer(String fromId, String toId, BigDecimal amount) {
        Account fromAccount = accounts.get(fromId);
        Account toAccount = accounts.get(toId);

        if(fromAccount == null) {
            throw new IllegalArgumentException("fromAccount User not found");
        }

        if(toAccount == null) {
            throw new IllegalArgumentException("toAccount User not found");
        }

        fromAccount.getLock().lock();
        toAccount.getLock().lock();

        try{
            if(fromAccount.getBalance().compareTo(amount) < 0) {
                throw new IllegalStateException("Insufficient Balance in fromAccount");
            }

            fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
            toAccount.setBalance(toAccount.getBalance().add(amount));
        }finally {
            toAccount.getLock().unlock();
            fromAccount.getLock().unlock();
        }

        String message = "Successfully transferred amount: " + amount + " from Account: " + fromId + " to Account: " + toId + ". So now current balance at 2nd Account is";
        return new TransactionResponse(true, message, toAccount.getBalance(), Instant.now());
    }
}
