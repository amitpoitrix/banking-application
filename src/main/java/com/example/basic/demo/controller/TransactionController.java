package com.example.basic.demo.controller;

import com.example.basic.demo.dto.TransactionResponse;
import com.example.basic.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/v1/accounts")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    // Create the account
    @PostMapping("create")
    public ResponseEntity<TransactionResponse> createAccount(@RequestParam String accountId, @RequestParam BigDecimal balance) {
        TransactionResponse transactionResponse = transactionService.createAccount(accountId, balance);
        return ResponseEntity.ok(transactionResponse);
    }

    // deposit amount in accountId
    @PostMapping("/{id}/deposit")
    public ResponseEntity<TransactionResponse> deposit(@PathVariable("id") String accountId, @RequestParam BigDecimal amount) throws IllegalAccessException {
        TransactionResponse deposit = transactionService.deposit(accountId, amount);
        return ResponseEntity.ok(deposit);
    }

    // withdraw the amount from accountId
    @PostMapping("/{id}/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(@PathVariable("id") String accountId, @RequestParam BigDecimal amount) {
        TransactionResponse transactionResponse = transactionService.withdraw(accountId, amount);
        return ResponseEntity.ok(transactionResponse);
    }

    // transfer the amount from Account A to B
    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(@RequestParam String fromId, @RequestParam String toId, @RequestParam BigDecimal amount) {
        TransactionResponse transactionResponse = transactionService.transfer(fromId, toId, amount);
        return ResponseEntity.ok(transactionResponse);
    }

    // Get balance from an account
    @GetMapping("/{id}/balance")
    public ResponseEntity<TransactionResponse> getBalance(@PathVariable("id") String accountId) {
        TransactionResponse transactionResponse = transactionService.getBalance(accountId);
        return ResponseEntity.ok(transactionResponse);
    }
}
