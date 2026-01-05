package com.luix.ecommerce.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableUtil {

    private PageableUtil() {}

    public static Pageable createPageable(int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection;
        try {
            sortDirection = Sort.Direction.fromString(direction);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid sort direction: " + direction);
        }
        return PageRequest.of(page,size,Sort.by(sortDirection, sortBy));
    }
}
