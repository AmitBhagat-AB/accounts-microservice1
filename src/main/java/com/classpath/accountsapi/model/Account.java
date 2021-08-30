package com.classpath.accountsapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(of = {"accountId", "customerName", "emailAddress", "panNumber"})
@Entity
public class Account {

    public static final double MIN_BALANCE = 25_000;

    @Id
    @Column(name="customer_id")
    private long accountId;

    private double balance;

    @OneToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

}