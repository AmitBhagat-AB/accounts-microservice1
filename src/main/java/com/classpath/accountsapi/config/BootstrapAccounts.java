package com.classpath.accountsapi.config;

import com.classpath.accountsapi.model.Account;
import com.classpath.accountsapi.model.Customer;
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
        Account account = Account.builder().balance(50000).build();

        Customer customer = Customer.builder()
                                    .aadharNumber("676545432")
                                    .customerName("Vinayak")
                                    .emailAddress("vinayak@gmail.com")
                                    .panNumber("AOYPO8777M").build();
       customer.addAccount(account);
       Account savedAccount = this.accountsRepository.save(account);

       Account account2 = Account.builder().balance(70000).build();

        Customer customer2 = Customer.builder()
                                    .aadharNumber("87788789")
                                    .customerName("Rashid")
                                    .emailAddress("rashid@gmail.com")
                                    .panNumber("AOYPP8777M").build();
       customer2.addAccount(account2);
       Account savedAccount2 = this.accountsRepository.save(account2);

    }
}