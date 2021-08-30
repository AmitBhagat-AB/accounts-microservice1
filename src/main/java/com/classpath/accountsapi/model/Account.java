package com.classpath.accountsapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@EqualsAndHashCode(of = {"accountId", "customerName", "emailAddress", "panNumber"})
@Entity
public class Account {

    public static final double MIN_BALANCE = 25_000;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long accountId;

    private double balance;

    private String customerName;

    private String emailAddress;

    private String panNumber;

    private String aadharNumber;


}