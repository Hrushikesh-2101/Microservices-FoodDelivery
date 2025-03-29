package com.foodSystem.product_service.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;         // e.g., "Margherita Pizza"

    @NotNull(message = "Price is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private double price;        // e.g., 9.99
    private String description;  // e.g., "Classic pizza with tomato and cheese"
    @NotNull(message = "Availability is mandatory")
    private boolean available;   // true = in stock, false = out of stock

    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;
    @ManyToOne
    @JoinColumn(name = "menu_id") // Foreign key linking to Menu
    private Menu menu;

    // Default constructor
    public Product() {}

    // Parameterized constructor
    public Product(Long id, String name, double price, String description, boolean available, Menu menu, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.available = available;
        this.menu = menu;
        this.categories = categories;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public Menu getMenu() { return menu; }
    public void setMenu(Menu menu) { this.menu = menu; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", available=" + available +
                ", menu=" + (menu != null ? menu.getName() : "null") +
                '}';
    }
}