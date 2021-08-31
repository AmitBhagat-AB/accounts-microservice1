package com.classpath.accountsapi.controller;

import com.classpath.accountsapi.model.Account;
import com.classpath.accountsapi.model.Transaction;
import com.classpath.accountsapi.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

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

    @GetMapping
    public Set<Account> fetchAccounts(
                            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                            @RequestParam(value = "size", defaultValue = "10") int pageSize,
                            @RequestParam(value = "sortBy", defaultValue = "accountId") String sortBy){

        return this.accountService.fetchAllAccounts(pageNo, pageSize, sortBy);

    }

    @GetMapping("/balance")
    public Set<Account> fetchAccountsByBalance(
            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @RequestParam(value = "gt" , defaultValue = "50000") double gt){
        return this.accountService.fetchAccountsGreaterThan(pageNo, pageSize, gt);
    }


}