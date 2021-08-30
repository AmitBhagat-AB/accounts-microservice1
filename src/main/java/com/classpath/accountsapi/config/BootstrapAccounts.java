package com.classpath.accountsapi.config;

import com.classpath.accountsapi.model.Account;
import com.classpath.accountsapi.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BootstrapAccounts implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    private AccountsRepository accountsRepository;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Account account = new Account();

        account.setBalance(50000);
        account.setAadharNumber("676545432");
        account.setCustomerName("Vinayak");
        account.setEmailAddress("vinayak@gmail.com");
        account.setPanNumber("AOYPO8777M");

        Account savedAccount = this.accountsRepository.save(account);
        System.out.println("Account saved :: " + savedAccount);

    }
}