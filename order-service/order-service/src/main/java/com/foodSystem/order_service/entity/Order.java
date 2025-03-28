package com.foodSystem.order_service.entity;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "orders") // "order" is a reserved keyword in SQL
public class Order {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;    // References User Service
    private Long productId; // References Product Service
    private String status;  // e.g., "PLACED", "DELIVERED"

    public Order() {
    }
    public Order(long userId, long productId, String status) {
        this.userId = userId;
        this.productId = productId;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
