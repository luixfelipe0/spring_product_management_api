package com.luix.ecommerce.dto.order;

import jakarta.validation.constraints.NotNull;


public record OrderItemRequestDTO(

        @NotNull
        Long productId,
        @NotNull
        Integer quantity

) {
}
