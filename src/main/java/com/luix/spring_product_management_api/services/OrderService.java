package com.luix.spring_product_management_api.services;

import com.luix.spring_product_management_api.entities.Order;
import com.luix.spring_product_management_api.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    public Order findById(Long id) {
        Optional<Order> order = repository.findById(id);
        return order.get();
    }

    public List<Order> findAll() {
        return repository.findAll();
    }

}
