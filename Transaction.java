package com.user.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction {
    private int id;
    private int orderId;
    private BigDecimal transactionAmount;
    private Timestamp transactionDate;

    public Transaction(int id, int orderId, BigDecimal transactionAmount, Timestamp transactionDate) {
        this.id = id;
        this.orderId = orderId;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
    }

    // Getters and setters
    // ...
}
