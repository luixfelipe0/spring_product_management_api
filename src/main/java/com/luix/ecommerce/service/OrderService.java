package com.luix.ecommerce.service;

import com.luix.ecommerce.dto.order.OrderRequestDTO;
import com.luix.ecommerce.dto.order.OrderResponseDTO;
import com.luix.ecommerce.entity.Order;
import com.luix.ecommerce.entity.OrderItem;
import com.luix.ecommerce.entity.User;
import com.luix.ecommerce.entity.enums.OrderStatus;
import com.luix.ecommerce.exception.RequestValidationException;
import com.luix.ecommerce.exception.ResourceNotFoundException;
import com.luix.ecommerce.mapper.OrderMapper;
import com.luix.ecommerce.repository.OrderRepository;
import com.luix.ecommerce.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final StockService stockService;

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        OrderMapper mapper,
                        OrderStatusValidator statusValidator,
                        OrderItemService orderItemService, StockService stockService
    ) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.statusValidator = statusValidator;
        this.orderItemService = orderItemService;
        this.stockService = stockService;
    }

    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO dto, String userEmail) {
        User client = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("This email does not exists."));

        if (!client.isActive()) {
            throw new RequestValidationException("User is inactive, can't proceed.");
        }

        Order order = mapper.toEntity(dto, client);
        order.setOrderStatus(OrderStatus.WAITING_PAYMENT);
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

    @Transactional
    public OrderResponseDTO updateStatus(Long id, OrderStatus newStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        OrderStatus current = order.getOrderStatus();
        OrderStatus target;

        try {
            target = newStatus;
        } catch (IllegalArgumentException e) {
            throw new RequestValidationException("Invalid order status: " + newStatus);
        }

        statusValidator.validate(current,target);

        if (target == OrderStatus.CANCELED && current != OrderStatus.CANCELED) {
            for (OrderItem item : order.getItems()) {
                stockService.releaseStock(item.getProduct(), item.getQuantity());
            }
        }

        order.setOrderStatus(target);
        order = orderRepository.save(order);

        return mapper.toDto(order);
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(orderId));

        if (order.getOrderStatus() != OrderStatus.CANCELED && order.getOrderStatus() != OrderStatus.PAID) {
            updateStatus(order.getId(), OrderStatus.CANCELED);

            order.getItems().forEach(orderItem -> {
                        stockService.releaseStock(orderItem.getProduct(), orderItem.getQuantity());
                    });
            logger.info("Order {} has been canceled and stock released.", orderId);
        } else {
            logger.warn("Attempt to cancel order {} failed. Current status: {}", orderId, order.getOrderStatus());
        }
    }

}
