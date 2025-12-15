package com.luix.ecommerce.entity.enums;

import java.util.Objects;

public enum OrderStatus {

    WAITING_PAYMENT(1, "WAITING_PAYMENT"),
    PAID(2, "PAID"),
    SHIPPED(3, "SHIPPED"),
    DELIVERED(4,"DELIVERED"),
    CANCELED(5, "CANCELED");

    private final Integer code;
    private final String value;

    OrderStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static OrderStatus valueOf(Integer code) {
        for (OrderStatus status : OrderStatus.values()) {
            if (Objects.equals(status.getCode(), code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid Order Status code: " + code);
    }
}
