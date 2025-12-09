package com.luix.ecommerce.dto.order;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record OrderItemRequestDTO(

        @NotNull
        Long productId,
        @NotNull
        Integer quantity,
        @NotNull
        BigDecimal price

) {
}
