package com.luix.ecommerce.dto.product;

import java.math.BigDecimal;
import java.util.Set;

public record ProductUpdateDTO(

        String name,
        String description,
        BigDecimal price,
        String imgUrl,
        Set<Long> categoriesIds

) {
}
