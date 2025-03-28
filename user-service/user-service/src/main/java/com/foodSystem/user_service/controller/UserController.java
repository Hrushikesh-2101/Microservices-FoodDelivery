package com.foodSystem.user_service.controller;

import com.foodSystem.user_service.entity.User;
import com.foodSystem.user_service.service.UserService;
import com.foodSystem.user_service.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Page<User> getAllUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDir,
            Pageable pageable) {

        Specification<User> spec = Specification.where(
                        name != null ? UserSpecification.hasName(name) : null)
                .and(email != null ? UserSpecification.hasEmail(email) : null)
                .and(phone != null ? UserSpecification.hasPhone(phone) : null)
                .and(address != null ? UserSpecification.hasAddress(address) : null);

        Sort sort = Sort.by(Sort.Direction.fromString(sortDir != null ? sortDir : "ASC"), sortBy != null ? sortBy : "id");
        return userService.getAllUsers(pageable, spec, sort);
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userService.updateUser(id, userDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}