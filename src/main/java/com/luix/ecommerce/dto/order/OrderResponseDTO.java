package com.luix.ecommerce.dto.order;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponseDTO(

        Long id,
        Instant moment,
        String status,
        Long clientId,
        List<OrderItemResponseDto> items,
        BigDecimal total

) {
}
