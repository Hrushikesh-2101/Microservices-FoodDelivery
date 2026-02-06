package com.foodSystem.product_service.repository;


import com.foodSystem.product_service.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;



@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Page<Product> findByNameContaining(String name, Pageable pageable);
    Page<Product> findByPriceBetween(double minPrice, double maxPrice, Pageable pageable);
    Page<Product> findByAvailable(boolean available, Pageable pageable);

    boolean existsByName(String name);
}