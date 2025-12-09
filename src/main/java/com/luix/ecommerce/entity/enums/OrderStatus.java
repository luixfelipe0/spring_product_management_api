package com.luix.ecommerce.entity.enums;

public enum OrderStatus {

    WAITING_PAYMENT("WAITING_PAYMENT"),
    PAID("PAID"),
    SHIPPED("SHIPPED"),
    DELIVERED("DELIVERED"),
    CANCELED("CANCELED");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
