package com.luix.spring_product_management_api.repositories;

import com.luix.spring_product_management_api.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
