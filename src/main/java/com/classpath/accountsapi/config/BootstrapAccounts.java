package com.classpath.accountsapi.config;

import com.classpath.accountsapi.model.Account;
import com.classpath.accountsapi.model.Address;
import com.classpath.accountsapi.model.Customer;
import com.classpath.accountsapi.repository.CustomerRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;

@Configuration
@Profile({"dev", "qa"})
public class BootstrapAccounts implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        Faker faker = new Faker();

        for(int index = 0; index < 20000; index ++){
            Account account = Account.builder()
                                        .balance(faker.number().randomDouble(2, 50000, 250000))
                                        .build();

            Customer customer = Customer.builder()
                    .aadharNumber(faker.idNumber().ssnValid())
                    .customerName(faker.name().fullName())
                    .birthDate(LocalDate.of(faker.number().numberBetween(1980, 2000), faker.number().numberBetween(6,10), faker.number().numberBetween(10, 20)))
                    .emailAddress(faker.internet().safeEmailAddress())
                    .panNumber(faker.idNumber().ssnValid()).build();
            customer.addAccount(account);

            Address homeAddress = Address.builder()
                                    .city(faker.address().city())
                                    .state(faker.address().state())
                                    .zipCode(faker.address().zipCode())
                                    .build();
            Address communicationAddress = Address.builder()
                                    .city(faker.address().city())
                                    .state(faker.address().state())
                                    .zipCode(faker.address().zipCode())
                                    .build();

            customer.addAddress(homeAddress);
            customer.addAddress(communicationAddress);

            this.customerRepository.save(customer);
        }
    }
}