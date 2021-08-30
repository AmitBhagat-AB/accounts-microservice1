package com.classpath.accountsapi.service;

import com.classpath.accountsapi.model.Account;
import com.classpath.accountsapi.model.Transaction;
import com.classpath.accountsapi.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountsRepository accountsRepository;

    public Transaction checkBalance(long accountId){
        Account account = this.accountsRepository.findById(accountId)
                                                    .orElseThrow(() -> new IllegalArgumentException("Account with Account id does not exists"));
        Transaction transaction = new Transaction();
        transaction.setAmount(account.getBalance());
        transaction.setAccountId(account.getAccountId());
        transaction.setType("CHECK_BALANCE");
        return transaction;
    }

    public Transaction deposit(long accountId, Transaction transaction){
        Account account = this.accountsRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account with Account id does not exists"));
        double updatedAccountBalance = account.getBalance() + transaction.getAmount();
        account.setBalance(updatedAccountBalance);
        this.accountsRepository.save(account);
        Transaction transactionOut = new Transaction();
        transactionOut.setAmount(account.getBalance());
        transactionOut.setAccountId(account.getAccountId());
        transactionOut.setAccountId(account.getAccountId());
        transactionOut.setType("DEPOSIT");
        transactionOut.setNotes(transaction.getNotes());
        return transactionOut;
    }

    public Transaction withdraw(long accountId, Transaction transaction) {
        Account account = this.accountsRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account with Account id does not exists"));

        double currentAccountBalance = account.getBalance();
        double withdrawalAmount = transaction.getAmount();
        if ((currentAccountBalance - withdrawalAmount) > Account.MIN_BALANCE){
            currentAccountBalance = currentAccountBalance - withdrawalAmount;
            account.setBalance(currentAccountBalance);
            this.accountsRepository.save(account);
            Transaction transactionOut = new Transaction();
            transactionOut.setAmount(withdrawalAmount);
            transactionOut.setAccountId(account.getAccountId());
            transactionOut.setType("WITHDRAW");
            transactionOut.setAccountId(account.getAccountId());
            transactionOut.setNotes(transaction.getNotes());
            return transaction;

        }
        throw new IllegalArgumentException("Insufficient Balance ");
    }
}