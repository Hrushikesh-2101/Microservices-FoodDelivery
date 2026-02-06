package com.foodSystem.user_service.controller;

import com.foodSystem.user_service.entity.User;
import com.foodSystem.user_service.service.UserService;
import com.foodSystem.user_service.specification.UserSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Controller", description = "API endpoints for managing users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Get all users", description = "Retrieves a paginated list of users with optional filtering and sorting")
    @GetMapping
    public Page<User> getAllUsers(
            @Parameter(description = "Filter by name") @RequestParam(required = false) String name,
            @Parameter(description = "Filter by email") @RequestParam(required = false) String email,
            @Parameter(description = "Filter by phone") @RequestParam(required = false) String phone,
            @Parameter(description = "Filter by address") @RequestParam(required = false) String address,
            @Parameter(description = "Field to sort by", example = "id") @RequestParam(required = false) String sortBy,
            @Parameter(description = "Sort direction (ASC/DESC)", example = "ASC") @RequestParam(required = false) String sortDir,
            Pageable pageable) {

        Specification<User> spec = Specification.where(
                        name != null ? UserSpecification.hasName(name) : null)
                .and(email != null ? UserSpecification.hasEmail(email) : null)
                .and(phone != null ? UserSpecification.hasPhone(phone) : null)
                .and(address != null ? UserSpecification.hasAddress(address) : null);

        Sort sort = Sort.by(Sort.Direction.fromString(sortDir != null ? sortDir : "ASC"), sortBy != null ? sortBy : "id");
        return userService.getAllUsers(pageable, spec, sort);
    }

    @Operation(summary = "Get user by ID", description = "Retrieves a specific user by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public Optional<User> getUserById(
            @Parameter(description = "User ID", required = true) @PathVariable Long id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "Create a new user", description = "Creates a new user")
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @Operation(summary = "Update a user", description = "Updates an existing user by ID")
    @PutMapping("/{id}")
    public User updateUser(
            @Parameter(description = "User ID", required = true) @PathVariable Long id, 
            @RequestBody User userDetails) {
        return userService.updateUser(id, userDetails);
    }

    @Operation(summary = "Delete a user", description = "Deletes a user by ID")
    @DeleteMapping("/{id}")
    public void deleteUser(
            @Parameter(description = "User ID", required = true) @PathVariable Long id) {
        userService.deleteUser(id);
    }
}