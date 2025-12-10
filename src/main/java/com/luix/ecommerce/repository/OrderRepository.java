package com.luix.ecommerce.repository;

import com.luix.ecommerce.dto.order.OrderResponseDTO;
import com.luix.ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByActiveTrue();

}
