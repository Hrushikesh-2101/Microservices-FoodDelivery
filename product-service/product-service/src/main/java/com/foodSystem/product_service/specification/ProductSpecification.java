// ProductSpecification.java
package com.foodSystem.product_service.specification;

import com.foodSystem.product_service.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ProductSpecification {

    public static Specification<Product> hasName(String name) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Product> hasPriceBetween(double minPrice, double maxPrice) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
    }

    public static Specification<Product> isAvailable(boolean available) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("available"), available);
    }

    // New filtering criteria
    public static Specification<Product> hasDescription(String description) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.like(root.get("description"), "%" + description + "%");
    }

    public static Specification<Product> belongsToMenu(Long menuId) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("menu").get("id"), menuId);
    }

    public static Specification<Product> hasCategoryIn(List<String> categories) {
        return (root, query, criteriaBuilder) -> root.get("category").in(categories);
    }
}