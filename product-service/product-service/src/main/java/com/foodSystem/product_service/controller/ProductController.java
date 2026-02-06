package com.foodSystem.product_service.controller;

import com.foodSystem.product_service.entity.Product;
import com.foodSystem.product_service.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Controller", description = "API endpoints for managing products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Get all products", description = "Retrieves a paginated list of all products")
    @GetMapping
    public Page<Product> getAllProducts(
            @Parameter(description = "Page number (0-indexed)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field to sort by", example = "id") @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction (asc/desc)", example = "asc") @RequestParam(defaultValue = "asc") String sortDir) {
        return productService.getAllProducts(page, size, sortBy, sortDir);
    }

    @Operation(summary = "Get product by ID", description = "Retrieves a specific product by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product found"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(
            @Parameter(description = "Product ID", required = true) @PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new product", description = "Creates a new product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product created successfully"),
        @ApiResponse(responseCode = "409", description = "Product with this name already exists")
    })
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.created(URI.create("/api/products/" + createdProduct.getId()))
                .body(createdProduct);
    }

    @Operation(summary = "Update a product", description = "Updates an existing product by ID")
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @Parameter(description = "Product ID", required = true) @PathVariable Long id, 
            @Valid @RequestBody Product productDetails) {
        Product updatedProduct = productService.updateProduct(id, productDetails);
        return ResponseEntity.ok(updatedProduct);
    }

    @Operation(summary = "Delete a product", description = "Deletes a product by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "Product ID", required = true) @PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Filter products by name", description = "Retrieves products filtered by name")
    @GetMapping("/filterByName")
    public Page<Product> getProductsByName(
            @Parameter(description = "Product name to search for", required = true) @RequestParam String name,
            @Parameter(description = "Page number", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10") @RequestParam(defaultValue = "10") int size) {
        return productService.getProductsByName(name, page, size);
    }

    @Operation(summary = "Filter products by price range", description = "Retrieves products within a price range")
    @GetMapping("/filterByPrice")
    public Page<Product> getProductsByPriceRange(
            @Parameter(description = "Minimum price", required = true, example = "5.0") @RequestParam double minPrice,
            @Parameter(description = "Maximum price", required = true, example = "50.0") @RequestParam double maxPrice,
            @Parameter(description = "Page number", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10") @RequestParam(defaultValue = "10") int size) {
        return productService.getProductsByPriceRange(minPrice, maxPrice, page, size);
    }

    @Operation(summary = "Filter products by availability", description = "Retrieves products filtered by availability status")
    @GetMapping("/filterByAvailability")
    public Page<Product> getProductsByAvailability(
            @Parameter(description = "Availability status", required = true, example = "true") @RequestParam boolean available,
            @Parameter(description = "Page number", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10") @RequestParam(defaultValue = "10") int size) {
        return productService.getProductsByAvailability(available, page, size);
    }

    @Operation(summary = "Search products", description = "Advanced search for products with multiple filters")
    @GetMapping("/search")
    public Page<Product> searchProducts(
            @Parameter(description = "Product name") @RequestParam(required = false) String name,
            @Parameter(description = "Minimum price") @RequestParam(required = false) Double minPrice,
            @Parameter(description = "Maximum price") @RequestParam(required = false) Double maxPrice,
            @Parameter(description = "Availability status") @RequestParam(required = false) Boolean available,
            @Parameter(description = "Product description") @RequestParam(required = false) String description,
            @Parameter(description = "Menu ID") @RequestParam(required = false) Long menuId,
            @Parameter(description = "Category names") @RequestParam(required = false) List<String> categories,
            @Parameter(description = "Page number", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Fields to sort by") @RequestParam(defaultValue = "id") List<String> sortBy,
            @Parameter(description = "Sort directions") @RequestParam(defaultValue = "asc") List<String> sortDir) {
        return productService.searchProducts(name, minPrice, maxPrice, available, description, menuId, categories, page, size, sortBy, sortDir);
    }
}