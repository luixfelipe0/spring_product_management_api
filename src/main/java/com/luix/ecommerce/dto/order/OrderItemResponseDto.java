package com.luix.ecommerce.dto.order;

import java.math.BigDecimal;

public record OrderItemResponseDto(

        Long productId,
        String productName,
        Integer quantity,
        BigDecimal price,
        BigDecimal subTotal

) {
}
