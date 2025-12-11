package com.luix.ecommerce.service;

import com.luix.ecommerce.dto.order.OrderItemRequestDTO;
import com.luix.ecommerce.dto.order.OrderRequestDTO;
import com.luix.ecommerce.dto.order.OrderResponseDTO;
import com.luix.ecommerce.entity.Order;
import com.luix.ecommerce.entity.OrderItem;
import com.luix.ecommerce.entity.Product;
import com.luix.ecommerce.entity.User;
import com.luix.ecommerce.entity.enums.OrderStatus;
import com.luix.ecommerce.exception.RequestValidationException;
import com.luix.ecommerce.exception.ResourceNotFoundException;
import com.luix.ecommerce.mapper.OrderMapper;
import com.luix.ecommerce.repository.OrderItemRepository;
import com.luix.ecommerce.repository.OrderRepository;
import com.luix.ecommerce.repository.ProductRepository;
import com.luix.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper mapper;
    private final OrderStatusValidator statusValidator;
    private final OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        ProductRepository productRepository,
                        OrderMapper mapper,
                        OrderStatusValidator statusValidator,
                        OrderItemRepository orderItemRepository
    ) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
        this.statusValidator = statusValidator;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO dto) {

        User client = userRepository.findById(dto.clientId())
                .orElseThrow(() -> new ResourceNotFoundException(dto.clientId()));

        if (!client.isActive()) {
            throw new RequestValidationException("User is inactive, can't proceed with order.");
        }

        Order order = mapper.toEntity(dto, client);
        order = orderRepository.save(order);

        for (OrderItemRequestDTO itemDto : dto.items()) {
            Product product = productRepository.findById(itemDto.productId())
                    .orElseThrow(() -> new ResourceNotFoundException(itemDto.productId()));

            OrderItem item = mapper.toEntityItem(
                    itemDto,
                    order,
                    product
            );

            orderItemRepository.save(item);
            order.getItems().add(item);
        }
        return mapper.toDto(order);
    }

    @Transactional(readOnly = true)
    public OrderResponseDTO findOrderById(Long id) {
        return mapper.toDto(
                orderRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(id))
        );
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> findAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public OrderResponseDTO updateStatus(Long id, String newStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        OrderStatus current = order.getOrderStatus();
        OrderStatus target;

        try {
            target = OrderStatus.valueOf(newStatus);
        } catch (IllegalArgumentException e) {
            throw new RequestValidationException("Invalid order status: " + newStatus);
        }

        statusValidator.validate(current,target);

        order.setOrderStatus(target);
        order = orderRepository.save(order);

        return mapper.toDto(order);
    }
}
