package com.luix.ecommerce.service;

import com.luix.ecommerce.dto.order.OrderItemRequestDTO;
import com.luix.ecommerce.entity.Order;
import com.luix.ecommerce.entity.OrderItem;
import com.luix.ecommerce.entity.Product;
import com.luix.ecommerce.exception.DuplicatedItemException;
import com.luix.ecommerce.exception.ResourceNotFoundException;
import com.luix.ecommerce.mapper.OrderMapper;
import com.luix.ecommerce.repository.OrderItemRepository;
import com.luix.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderItemService {

    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final StockService stockService;

    public OrderItemService(
            ProductRepository productRepository,
            OrderItemRepository orderItemRepository,
            OrderMapper orderMapper,
            StockService stockService
    ) {
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderMapper = orderMapper;
        this.stockService = stockService;
    }

    @Transactional
    public void createItemsForOrder(Order order, List<OrderItemRequestDTO> itemsDto) {

        Set<Long> uniqueIds = new HashSet<>();
        for (OrderItemRequestDTO dto : itemsDto) {
            if(!uniqueIds.add(dto.productId())) {
                throw new DuplicatedItemException("Duplicated product on order: " + dto.productId());
            }
        }

        Map<Long, Product> productMap = productRepository.findAllById(
                itemsDto.stream()
                        .map(OrderItemRequestDTO::productId)
                        .toList()
        ).stream().collect(Collectors.toMap(Product::getId, Function.identity()));

        stockService.reserveStock(productMap, itemsDto);

        List<OrderItem> items = itemsDto.stream()
                .map(itemDto -> {
                    Product product = productMap.get(itemDto.productId());
                    if (product == null) {
                        throw new ResourceNotFoundException(itemDto.productId());
                    }
                    return orderMapper.toEntityItem(itemDto, order, product);
                })
                .toList();

        orderItemRepository.saveAll(items);
        order.getItems().addAll(items);
    }
}
