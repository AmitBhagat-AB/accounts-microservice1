package com.classpath.accountsapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@EqualsAndHashCode(of = {"accountId"})
@Entity
@ToString(exclude={"customer"})
public class Account {

    public static final double MIN_BALANCE = 25_000;

    @Id
    @Column(name="customer_id")
    private long accountId;

    private double balance;

    @OneToOne
    @MapsId
    @JoinColumn(name="customer_id")
    private Customer customer;

}