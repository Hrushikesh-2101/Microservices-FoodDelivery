package com.foodSystem.order_service.service;

import com.foodSystem.order_service.entity.Order;
import com.foodSystem.order_service.repository.OrderRepository;
import com.foodSystem.order_service.specification.OrderSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, Order orderDetails) {
    Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setUserId(orderDetails.getUserId());
        order.setProductId(orderDetails.getProductId());
        order.setStatus(orderDetails.getStatus());
         if(orderRepository.save(order).getId()>0) {
             return order;
         }
         else {
                throw new RuntimeException("Error while creating order");
         }
    }

    public void deleteOrder(Long id) {
        if(!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found with id " + id);
        }
        orderRepository.deleteById(id);
    }

    public Page<Order> getOrders(Pageable pageable, Sort sort) {
        return orderRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort));
    }

    public Page<Order> getFilteredOrders(Pageable pageable, String status, Long userId, Long productId) {
        Specification<Order> spec = Specification.where(null);
        if (status != null) {
            spec = spec.and(OrderSpecification.hasStatus(status));
        }
        if (userId != null) {
            spec = spec.and(OrderSpecification.hasUserId(userId));
        }
        if (productId != null) {
            spec = spec.and(OrderSpecification.hasProductId(productId));
        }
        return orderRepository.findAll(spec, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
    }

    public Page<Order> searchOrders(Pageable pageable, String keyword) {
        Specification<Order> spec = Specification.where(OrderSpecification.containsKeyword(keyword));
        return orderRepository.findAll(spec, pageable);
    }


}
