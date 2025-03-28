package com.foodSystem.user_service.service;

import com.foodSystem.user_service.entity.User;
import com.foodSystem.user_service.exception.UserAlreadyExistsException;
import com.foodSystem.user_service.exception.UserNotFoundException;
import com.foodSystem.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Page<User> getAllUsers(Pageable pageable, Specification<User> spec, Sort sort) {
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        return userRepository.findAll(spec, sortedPageable);
    }


    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id " + id)));
    }

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("User already exists with email " + user.getEmail());
        }
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setPhone(userDetails.getPhone());
        user.setAddress(userDetails.getAddress());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with id " + id);
        }
        userRepository.deleteById(id);
    }
}
