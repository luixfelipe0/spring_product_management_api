package com.luix.ecommerce.service;

import com.luix.ecommerce.entity.enums.OrderStatus;
import com.luix.ecommerce.exception.RequestValidationException;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

@Component
public class OrderStatusValidator {

    private final Map<OrderStatus, Set<OrderStatus>> transitions = new EnumMap<>(OrderStatus.class);

    public OrderStatusValidator() {
        transitions.put(OrderStatus.WAITING_PAYMENT, EnumSet.of(
                OrderStatus.PAID, OrderStatus.CANCELED
        ));

        transitions.put(OrderStatus.PAID, EnumSet.of(
                OrderStatus.SHIPPED, OrderStatus.CANCELED
        ));

        transitions.put(OrderStatus.SHIPPED, EnumSet.of(
                OrderStatus.DELIVERED
        ));

        transitions.put(OrderStatus.DELIVERED, EnumSet.noneOf(OrderStatus.class));
        transitions.put(OrderStatus.CANCELED, EnumSet.noneOf(OrderStatus.class));
    }

    public void validate(OrderStatus current, OrderStatus target) {

        Set<OrderStatus> allowed = transitions.get(current);

        if (allowed == null || !allowed.contains(target)) {
            throw new RequestValidationException(
                    "Invalid status transition: " + current + " -> " + target
            );
        }
    }
}
