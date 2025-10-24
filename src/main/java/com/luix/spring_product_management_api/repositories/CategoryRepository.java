package com.luix.spring_product_management_api.repositories;

import com.luix.spring_product_management_api.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
