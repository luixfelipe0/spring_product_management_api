package com.luix.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luix.ecommerce.entity.pk.OrderItemPk;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "tb_order_items")
public class OrderItem implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private final OrderItemPk id = new OrderItemPk();

    private Integer quantity;
    private BigDecimal price;

    public OrderItem() {
    }

    public OrderItem(Order order, Product product, Integer quantity, BigDecimal price) {
        id.setOrder(order);
        id.setProduct(product);
        this.quantity = quantity;
        this.price = price;
    }

    @JsonIgnore
    public Order getOrder() {
        return id.getOrder();
    }
    
    public void setOrder(Order order) {
        id.setOrder(order);
    }

    @JsonIgnore
    public Product getProduct() {
        return id.getProduct();
    }
    
    public void setProduct(Product product) {
        id.setProduct(product);
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSubTotal() {
        if (quantity == null || price == null) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(quantity).multiply(price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(id, orderItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
