package com.classpath.accountsapi.controller;

import com.classpath.accountsapi.model.Transaction;
import com.classpath.accountsapi.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
public class AccountsController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{accountId}/statement")
    public Transaction checkAccountBalance(@PathVariable("accountId") long accountId){
        return accountService.checkBalance(accountId);
    }

    @PutMapping("/transaction/{accountId}")
    public Transaction withdraw(@PathVariable("accountId") long accountId, @Valid @RequestBody Transaction transaction){
        if (transaction.getType().equalsIgnoreCase("WITHDRAW")){
            return accountService.withdraw(accountId, transaction );
        }
        return accountService.deposit(accountId, transaction );
    }
}