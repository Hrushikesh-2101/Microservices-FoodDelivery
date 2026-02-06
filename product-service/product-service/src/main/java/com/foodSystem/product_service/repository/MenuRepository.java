package com.foodSystem.product_service.repository;

import com.foodSystem.product_service.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Id;

public interface MenuRepository extends JpaRepository<Menu, Long> {}
