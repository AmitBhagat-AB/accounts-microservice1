package com.classpath.accountsapi.controller;

import com.classpath.accountsapi.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountsController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{accountId}")
    public double checkAccountBalance(@PathVariable("accountId") long accountId){
        return accountService.checkBalance(accountId);
    }

    @PutMapping("/withdraw/{accountId}/{amount}")
    public double withdraw(@PathVariable("accountId") long accountId, @PathVariable("amount") double amount){
        return accountService.withdraw(accountId, amount );
    }

    @PutMapping("/deposit/{accountId}/{amount}")
    public double deposit(@PathVariable("accountId") long accountId, @PathVariable("amount") double amount){
        return accountService.deposit(accountId, amount );
    }
}