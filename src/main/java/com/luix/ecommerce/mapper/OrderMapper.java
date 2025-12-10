package com.luix.ecommerce.mapper;

import com.luix.ecommerce.dto.order.OrderItemRequestDTO;
import com.luix.ecommerce.dto.order.OrderItemResponseDto;
import com.luix.ecommerce.dto.order.OrderRequestDTO;
import com.luix.ecommerce.dto.order.OrderResponseDTO;
import com.luix.ecommerce.entity.Order;
import com.luix.ecommerce.entity.OrderItem;
import com.luix.ecommerce.entity.Product;
import com.luix.ecommerce.entity.User;
import com.luix.ecommerce.entity.enums.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public Order toEntity(OrderRequestDTO dto, User client) {
        Order order = new Order();
        order.setClient(client);
        order.setOrderStatus(OrderStatus.WAITING_PAYMENT);
        return order;
    }

    public OrderItem toEntityItem(OrderItemRequestDTO dto, Order order, Product product) {
        return new OrderItem(
                order,
                product,
                dto.quantity(),
                product.getPrice()
        );
    }

    public OrderResponseDTO toDto(Order order) {
        return new OrderResponseDTO(
                order.getId(),
                order.getCreatedAt(),
                order.getOrderStatus().name(),
                order.getClient().getId(),
                order.getItems()
                        .stream()
                        .map(this::toDtoItem)
                        .toList(),
                order.getTotal()
        );
    }

    public OrderItemResponseDto toDtoItem(OrderItem item) {
        return new OrderItemResponseDto(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getPrice(),
                item.getSubTotal()
        );
    }

}
