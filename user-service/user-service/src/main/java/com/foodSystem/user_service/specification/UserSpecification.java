package com.foodSystem.user_service.specification;

import com.foodSystem.user_service.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> hasName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<User> hasEmail(String email) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("email"), "%" + email + "%");
    }

    public static Specification<User> hasPhone(String phone) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("phone"), "%" + phone + "%");
    }

    public static Specification<User> hasAddress(String address) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("address"), "%" + address + "%");
    }

    // Add more specifications as needed
}
