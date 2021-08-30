package com.classpath.accountsapi.service;

import com.classpath.accountsapi.model.Account;
import com.classpath.accountsapi.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountsRepository accountsRepository;

    public double checkBalance(long accountId){
        Account account = this.accountsRepository.findById(accountId)
                                                    .orElseThrow(() -> new IllegalArgumentException("Account with Account id does not exists"));
        return account.getBalance();
    }

    public double deposit(long accountId, double amount){
        Account account = this.accountsRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account with Account id does not exists"));
        double updatedAccountBalance = account.getBalance() + amount;
        account.setBalance(updatedAccountBalance);

        return updatedAccountBalance;
    }

    public double withdraw(long accountId, double withdrawalAmount) {
        Account account = this.accountsRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account with Account id does not exists"));

        double currentAccountBalance = account.getBalance();
        if ((currentAccountBalance - withdrawalAmount) > Account.MIN_BALANCE){
            currentAccountBalance = currentAccountBalance - withdrawalAmount;
            account.setBalance(currentAccountBalance);
            return withdrawalAmount;
        }
        throw new IllegalArgumentException("Insufficient Balance ");
    }
}