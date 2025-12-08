package com.luix.ecommerce.dto.product;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

public record ProductResponseDTO(

        Long id,
        String name,
        String description,
        BigDecimal price,
        String imgUrl,
        Boolean active,
        Instant createdAt,
        Instant updatedAt,
        Set<Long> categoriesIds

) {
}
