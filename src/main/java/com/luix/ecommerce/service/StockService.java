package com.luix.ecommerce.service;

import com.luix.ecommerce.dto.order.OrderItemRequestDTO;
import com.luix.ecommerce.entity.Product;
import com.luix.ecommerce.exception.StockInsufficientException;
import com.luix.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class StockService {

    private final ProductRepository repository;

    public StockService(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void reserveStock(Map<Long, Product> productMap, List<OrderItemRequestDTO> items) {

        for (OrderItemRequestDTO item : items) {
            Product product = productMap.get(item.productId());

            if (product.getStockQuantity() < item.quantity()) {
                throw new StockInsufficientException(
                        "Insufficient stock for product: " + product.getName() +
                                ". Available: " + product.getStockQuantity() +
                                ", Solicited: " + item.quantity()
                );
            }
            product.setStockQuantity(product.getStockQuantity() - item.quantity());
            repository.save(product);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void releaseStock(Product product, Integer quantity) {
        product.setStockQuantity(product.getStockQuantity() + quantity);
        repository.save(product);
    }
}
