package com.classpath.accountsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

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

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private Account account;

    public void addAccount(Account account){
        this.account = account;
        account.setCustomer(this);
    }
}