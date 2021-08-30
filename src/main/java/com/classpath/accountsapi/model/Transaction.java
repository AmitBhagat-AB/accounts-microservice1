package com.classpath.accountsapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Transaction {
    private long accountId;
    private String type;
    private double amount;
    private String notes;
}