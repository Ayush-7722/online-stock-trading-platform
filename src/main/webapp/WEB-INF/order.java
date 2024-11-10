package com.user.model;

import java.math.BigDecimal;

public class Order {
    private int id;
    private int userId;
    private int stockId;
    private String type; // "BUY" or "SELL"
    private int quantity;
    private BigDecimal price;
    private String status; // "PENDING", "COMPLETED", "CANCELLED"

    public Order(int id, int userId, int stockId, String type, int quantity, BigDecimal price, String status) {
        this.id = id;
        this.userId = userId;
        this.stockId = stockId;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }

    // Getters and setters
    // ...
}
