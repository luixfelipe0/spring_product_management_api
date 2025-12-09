package com.luix.ecommerce.dto.order;

import com.luix.ecommerce.entity.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record OrderUpdateDTO(

        @NotNull
        OrderStatus status

) {
}
