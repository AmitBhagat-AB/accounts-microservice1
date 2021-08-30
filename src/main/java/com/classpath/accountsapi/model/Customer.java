package com.classpath.accountsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(of={"cutomerId", "customerName", "emailAddress"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long customerId;

    private String customerName;

    private String emailAddress;

    private String panNumber;

    private String aadharNumber;

    private LocalDate birthDate;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Address> addressSet = new HashSet<>();

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private Account account;

    public void addAccount(Account account){
        this.account = account;
        account.setCustomer(this);
    }

    public void addAddress(Address address){
        if (addressSet == null){
            addressSet = new HashSet<>();
        }
        addressSet.add(address);
        address.setCustomer(this);
    }
}