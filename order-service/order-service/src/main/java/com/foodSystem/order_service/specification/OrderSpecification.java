package com.foodSystem.order_service.specification;

import com.foodSystem.order_service.entity.Order;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecification {

    public static Specification<Order> hasStatus(String status) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Order> hasUserId(Long userId) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("userId"), userId);
    }

    public static Specification<Order> hasProductId(Long productId) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("productId"), productId);
    }

    public static Specification<Order> containsKeyword(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.like(root.get("status"), "%" + keyword + "%"),
                        criteriaBuilder.like(root.get("userId").as(String.class), "%" + keyword + "%"),
                        criteriaBuilder.like(root.get("productId").as(String.class), "%" + keyword + "%")
                );
    }
}
