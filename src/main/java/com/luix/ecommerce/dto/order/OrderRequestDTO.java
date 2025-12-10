package com.luix.ecommerce.dto.order;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequestDTO(

        @NotNull
        Long clientId,
        @NotNull
        List<OrderItemRequestDTO> items

) {
}
