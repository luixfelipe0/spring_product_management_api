package com.luix.ecommerce.dto;

import java.time.Instant;

public record CategoryResponseDTO(

        Long id,
        String name,
        String description,
        Boolean active,
        Instant createdAt,
        Instant updatedAt

) {
}
