package com.luix.ecommerce.entity.enums;

import java.util.Objects;

public enum PaymentStatus {

    WAITING_PAYMENT(1, "PENDING"),
    PAID(2, "AUTHORIZED"),
    SHIPPED(3, "PAID"),
    DELIVERED(4,"REFUNDED"),
    CANCELED(5, "CANCELED"),
    FAILED(6, "FAILED");

    private final Integer code;
    private final String description;

    PaymentStatus(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return description;
    }

    public static PaymentStatus valueOf(Integer code) {
        for (PaymentStatus status : PaymentStatus.values()) {
            if (Objects.equals(status.getCode(), code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid Payment Status code: " + code);
    }
}
