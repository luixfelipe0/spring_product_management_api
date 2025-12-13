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
    private final OrderMapper mapper;
    private final OrderStatusValidator statusValidator;
    private final OrderItemService orderItemService;

    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        OrderMapper mapper,
                        OrderStatusValidator statusValidator,
                        OrderItemService orderItemService
    ) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.statusValidator = statusValidator;
        this.orderItemService = orderItemService;
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

        orderItemService.createItemsForOrder(order, dto.items());

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
