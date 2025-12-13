package com.luix.ecommerce.service;

import com.luix.ecommerce.dto.order.OrderItemRequestDTO;
import com.luix.ecommerce.entity.Order;
import com.luix.ecommerce.entity.OrderItem;
import com.luix.ecommerce.entity.Product;
import com.luix.ecommerce.exception.ResourceNotFoundException;
import com.luix.ecommerce.mapper.OrderMapper;
import com.luix.ecommerce.repository.OrderItemRepository;
import com.luix.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderItemService {

    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;

    public OrderItemService(
            ProductRepository productRepository,
            OrderItemRepository orderItemRepository,
            OrderMapper orderMapper
    ) {
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional
    public void createItemsForOrder(Order order, List<OrderItemRequestDTO> itemsDto) {
        for (OrderItemRequestDTO itemDto : itemsDto) {
            Product product = productRepository.findById(itemDto.productId())
                    .orElseThrow(() -> new ResourceNotFoundException(itemDto.productId()));

            OrderItem item = orderMapper.toEntityItem(
                    itemDto,
                    order,
                    product
            );

            orderItemRepository.save(item);
            order.getItems().add(item);
        }
    }
}
