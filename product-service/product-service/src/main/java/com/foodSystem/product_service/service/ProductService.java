package com.foodSystem.product_service.service;

import com.foodSystem.product_service.entity.Product;
import com.foodSystem.product_service.exception.ProductAlreadyExistsException;
import com.foodSystem.product_service.exception.ProductNotFoundException;
import com.foodSystem.product_service.repository.ProductRepository;
import com.foodSystem.product_service.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> getAllProducts(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(Sort.Order.asc(sortBy))
                : Sort.by(Sort.Order.desc(sortBy));
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAll(pageable);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        if (productRepository.existsByName(product.getName())) {
      throw new ProductAlreadyExistsException(
          "Product already exists with name " + product.getName());
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
    Product product =
        productRepository
            .findById(id)
            .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + id));

        if (productDetails.getName() != null) product.setName(productDetails.getName());
        if (productDetails.getPrice() > 0) product.setPrice(productDetails.getPrice());
        if (productDetails.getDescription() != null) product.setDescription(productDetails.getDescription());
        product.setAvailable(productDetails.isAvailable()); // Always update availability
        if (productDetails.getMenu() != null) product.setMenu(productDetails.getMenu());
        if (productDetails.getCategories() != null) product.setCategories(productDetails.getCategories());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + id));
        productRepository.delete(product);
    }

    public Page<Product> getProductsByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByNameContaining(name, pageable);
    }

    public Page<Product> getProductsByPriceRange(double minPrice, double maxPrice, int page, int size) {
        if (minPrice > maxPrice) {
            throw new IllegalArgumentException("minPrice cannot be greater than maxPrice");
        }
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByPriceBetween(minPrice, maxPrice, pageable);
    }

    public Page<Product> getProductsByAvailability(boolean available, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByAvailable(available, pageable);
    }

    public Page<Product> searchProducts(String name, Double minPrice, Double maxPrice, Boolean available,
                                        String description, Long menuId, List<String> categories, int page, int size,
                                        List<String> sortBy, List<String> sortDir) {
        Specification<Product> spec = Specification.where(null);

        if (name != null && !name.isEmpty()) {
            spec = spec.and(ProductSpecification.hasName(name));
        }
        if (minPrice != null && maxPrice != null) {
            if (minPrice > maxPrice) throw new IllegalArgumentException("minPrice cannot be greater than maxPrice");
            spec = spec.and(ProductSpecification.hasPriceBetween(minPrice, maxPrice));
        }
        if (available != null) {
            spec = spec.and(ProductSpecification.isAvailable(available));
        }
        if (description != null && !description.isEmpty()) {
            spec = spec.and(ProductSpecification.hasDescription(description));
        }
        if (menuId != null) {
            spec = spec.and(ProductSpecification.belongsToMenu(menuId));
        }
        if (categories != null && !categories.isEmpty()) {
            spec = spec.and(ProductSpecification.hasCategoryIn(categories));
        }

        Sort sort = Sort.by(Sort.Order.asc("id")); // Default sort
        for (int i = 0; i < sortBy.size() && i < sortDir.size(); i++) {
            String direction = sortDir.get(i).equalsIgnoreCase("asc") ? "asc" : "desc";
            sort = sort.and(Sort.by(Sort.Order.by(sortBy.get(i)).with(Sort.Direction.fromString(direction))));
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAll(spec, pageable);
    }
}