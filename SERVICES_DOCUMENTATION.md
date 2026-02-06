# Microservices Food Delivery System - Complete Documentation

## Overview
This project consists of **3 microservices**:
1. **User Service** - Manages user accounts and profiles
2. **Product Service** - Manages products, menus, and categories
3. **Order Service** - Manages orders placed by users

---

## 1. USER SERVICE

### 1.1 Service Overview
- **Base URL**: `/api/users`
- **Purpose**: Manages user registration, authentication data, and user profiles
- **Database Table**: `users`

### 1.2 API Endpoints

| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `GET` | `/api/users` | Get all users (paginated with filters) | Query params: `name`, `email`, `phone`, `address`, `sortBy`, `sortDir`, `page`, `size` | `Page<User>` |
| `GET` | `/api/users/{id}` | Get user by ID | Path variable: `id` | `Optional<User>` |
| `POST` | `/api/users` | Create a new user | `User` object | `User` |
| `PUT` | `/api/users/{id}` | Update user by ID | Path variable: `id`, `User` object | `User` |
| `DELETE` | `/api/users/{id}` | Delete user by ID | Path variable: `id` | `void` |

#### API Usage Examples:

**Get all users with filters:**
```
GET /api/users?name=John&email=john@example.com&page=0&size=10&sortBy=id&sortDir=ASC
```

**Get user by ID:**
```
GET /api/users/1
```

**Create user:**
```
POST /api/users
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "password": "password123",
  "phone": "1234567890",
  "address": "123 Main St"
}
```

**Update user:**
```
PUT /api/users/1
Content-Type: application/json

{
  "name": "John Updated",
  "email": "john.updated@example.com",
  "password": "newpassword",
  "phone": "9876543210",
  "address": "456 New St"
}
```

**Delete user:**
```
DELETE /api/users/1
```

### 1.3 Entity/POJO Classes

#### User Entity
```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 100)
    private String name;
    
    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    @Size(min = 6)
    private String password;
    
    @NotBlank
    private String phone;
    
    @NotBlank
    @Size(max = 255)
    private String address;
}
```

**Fields:**
- `id` (Long): Auto-generated primary key
- `name` (String): User's full name (max 100 chars)
- `email` (String): User's email address (unique, validated)
- `password` (String): User's password (min 6 chars)
- `phone` (String): User's phone number
- `address` (String): User's address (max 255 chars)

### 1.4 Relationships/Joins
**No JPA relationships** - User entity is standalone. However, it's referenced by:
- **Order Service**: Uses `userId` (Long) as a foreign key reference (not a JPA relationship, just an ID reference)

---

## 2. PRODUCT SERVICE

### 2.1 Service Overview
- **Base URL**: `/api/products`
- **Purpose**: Manages food products, menus, and categories
- **Database Tables**: `products`, `menus`, `categories`, `product_category` (join table)

### 2.2 API Endpoints

| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `GET` | `/api/products` | Get all products (paginated) | Query params: `page`, `size`, `sortBy`, `sortDir` | `Page<Product>` |
| `GET` | `/api/products/{id}` | Get product by ID | Path variable: `id` | `ResponseEntity<Product>` |
| `POST` | `/api/products` | Create a new product | `Product` object | `ResponseEntity<Product>` |
| `PUT` | `/api/products/{id}` | Update product by ID | Path variable: `id`, `Product` object | `ResponseEntity<Product>` |
| `DELETE` | `/api/products/{id}` | Delete product by ID | Path variable: `id` | `ResponseEntity<Void>` |
| `GET` | `/api/products/filterByName` | Filter products by name | Query params: `name`, `page`, `size` | `Page<Product>` |
| `GET` | `/api/products/filterByPrice` | Filter products by price range | Query params: `minPrice`, `maxPrice`, `page`, `size` | `Page<Product>` |
| `GET` | `/api/products/filterByAvailability` | Filter products by availability | Query params: `available`, `page`, `size` | `Page<Product>` |
| `GET` | `/api/products/search` | Advanced search with multiple filters | Query params: `name`, `minPrice`, `maxPrice`, `available`, `description`, `menuId`, `categories[]`, `page`, `size`, `sortBy[]`, `sortDir[]` | `Page<Product>` |

#### API Usage Examples:

**Get all products:**
```
GET /api/products?page=0&size=10&sortBy=id&sortDir=asc
```

**Get product by ID:**
```
GET /api/products/1
```

**Create product:**
```
POST /api/products
Content-Type: application/json

{
  "name": "Margherita Pizza",
  "price": 9.99,
  "description": "Classic pizza with tomato and cheese",
  "available": true,
  "menu": {"id": 1},
  "categories": [{"id": 1, "name": "Vegetarian"}]
}
```

**Filter by name:**
```
GET /api/products/filterByName?name=Pizza&page=0&size=10
```

**Filter by price range:**
```
GET /api/products/filterByPrice?minPrice=5.0&maxPrice=50.0&page=0&size=10
```

**Advanced search:**
```
GET /api/products/search?name=Pizza&minPrice=5.0&maxPrice=50.0&available=true&menuId=1&categories=Vegetarian&categories=Vegan&page=0&size=10&sortBy=price&sortDir=asc
```

### 2.3 Entity/POJO Classes

#### Product Entity
```java
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotBlank
    private String name;
    
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private double price;
    
    private String description;
    
    @NotNull
    private boolean available;
    
    @ManyToMany
    @JoinTable(
        name = "product_category",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;
    
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;
}
```

**Fields:**
- `id` (Long): Auto-generated primary key
- `name` (String): Product name (required, unique)
- `price` (double): Product price (must be > 0)
- `description` (String): Product description
- `available` (boolean): Availability status
- `categories` (List<Category>): Many-to-many relationship with Category
- `menu` (Menu): Many-to-one relationship with Menu

#### Category Entity
```java
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotBlank
    private String name;
    
    @ManyToMany(mappedBy = "categories")
    private List<Product> products;
}
```

**Fields:**
- `id` (Long): Auto-generated primary key
- `name` (String): Category name (required)
- `products` (List<Product>): Many-to-many relationship with Product (inverse side)

#### Menu Entity
```java
@Entity
@Table(name = "menus")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;
}
```

**Fields:**
- `id` (Long): Auto-generated primary key
- `name` (String): Menu name
- `products` (List<Product>): One-to-many relationship with Product

### 2.4 Relationships/Joins

#### 1. Product ↔ Category (Many-to-Many)
- **Join Table**: `product_category`
- **Columns**: 
  - `product_id` (FK to products.id)
  - `category_id` (FK to categories.id)
- **Explanation**: 
  - A Product can belong to multiple Categories (e.g., "Vegetarian" and "Italian")
  - A Category can contain multiple Products
  - Managed by `@JoinTable` on Product side, `mappedBy` on Category side
  - Uses `@JsonManagedRefi want to uderence` on Product and `@JsonBackReference` on Category to prevent JSON circular references

#### 2. Product ↔ Menu (Many-to-One)
- **Foreign Key**: `menu_id` in `products` table
- **Explanation**:
  - A Product belongs to one Menu (e.g., "Pizza Menu")
  - A Menu can have many Products
  - Managed by `@ManyToOne` on Product, `@OneToMany` on Menu
  - Cascade: ALL (when Menu is deleted, Products are deleted)
  - Fetch: LAZY (Menu products loaded on demand)

**Entity Relationship Diagram:**
```
Menu (1) ────────< (Many) Product
                          │
                          │ Many-to-Many
                          │
                    Category (Many)
```

---

## 3. ORDER SERVICE

### 3.1 Service Overview
- **Base URL**: `/orders`
- **Purpose**: Manages orders placed by users for products
- **Database Table**: `orders`

### 3.2 API Endpoints

| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `GET` | `/orders` | Get all orders | None | `List<Order>` |
| `GET` | `/orders/{id}` | Get order by ID | Path variable: `id` | `ResponseEntity<Order>` |
| `POST` | `/orders` | Create a new order | `Order` object | `Order` |
| `PUT` | `/orders/{id}` | Update order by ID | Path variable: `id`, `Order` object | `ResponseEntity<Order>` |
| `DELETE` | `/orders/{id}` | Delete order by ID | Path variable: `id` | `ResponseEntity<Void>` |
| `GET` | `/orders/page` | Get paginated orders | Query params: `page`, `size`, `sortBy` | `Page<Order>` |
| `GET` | `/orders/filter` | Filter orders | Query params: `status`, `userId`, `productId`, `page`, `size` | `Page<Order>` |
| `GET` | `/orders/search` | Search orders by keyword | Query params: `keyword`, `page`, `size` | `Page<Order>` |

#### API Usage Examples:

**Get all orders:**
```
GET /orders
```

**Get order by ID:**
```
GET /orders/1
```

**Create order:**
```
POST /orders
Content-Type: application/json

{
  "userId": 1,
  "productId": 5,
  "status": "PLACED"
}
```

**Update order:**
```
PUT /orders/1
Content-Type: application/json

{
  "userId": 1,
  "productId": 5,
  "status": "DELIVERED"
}
```

**Get paginated orders:**
```
GET /orders/page?page=0&size=10&sortBy=id
```

**Filter orders:**
```
GET /orders/filter?status=PLACED&userId=1&productId=5&page=0&size=10
```

**Search orders:**
```
GET /orders/search?keyword=PLACED&page=0&size=10
```

### 3.3 Entity/POJO Classes

#### Order Entity
```java
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Long userId;    // References User Service
    private Long productId; // References Product Service
    private String status;  // e.g., "PLACED", "DELIVERED"
}
```

**Fields:**
- `id` (Long): Auto-generated primary key
- `userId` (Long): Reference to User ID (foreign key to User Service)
- `productId` (Long): Reference to Product ID (foreign key to Product Service)
- `status` (String): Order status (e.g., "PLACED", "PREPARING", "DELIVERED", "CANCELLED")

### 3.4 Relationships/Joins

**No JPA relationships** - Order entity uses **Long references** instead of JPA relationships:
- `userId` (Long): References User Service (not a JPA `@ManyToOne` relationship)
- `productId` (Long): References Product Service (not a JPA `@ManyToOne` relationship)

**Why?** This is a microservices pattern where:
- Each service maintains its own database
- Cross-service relationships are handled via **service-to-service communication** (REST calls)
- Foreign keys are stored as simple Long values, not JPA entity references
- This maintains **service independence** and **database decoupling**

**Logical Relationships:**
```
Order (Many) ──> User (1)     [via userId]
Order (Many) ──> Product (1)  [via productId]
```

**Note**: To get full User or Product details in an Order response, the Order Service would need to make REST API calls to:
- User Service: `GET /api/users/{userId}`
- Product Service: `GET /api/products/{productId}`

---

## Summary of Relationships Across Services

### Within Product Service:
1. **Product ↔ Category**: Many-to-Many (via `product_category` join table)
2. **Product ↔ Menu**: Many-to-One (via `menu_id` foreign key)

### Cross-Service References (No JPA Relationships):
1. **Order → User**: Reference via `userId` (Long)
2. **Order → Product**: Reference via `productId` (Long)

### Design Pattern:
- **Product Service**: Uses traditional JPA relationships (same database)
- **Order Service**: Uses ID references (different database/microservice)
- **User Service**: Standalone entity (no relationships)

---

## Notes on DTOs

**Current Status**: DTO classes exist but are empty:
- `ProductRequest.java` - Empty (not used)
- `ProductResponse.java` - Empty (not used)
- `OrderRequest.java` - Empty (not used)
- `OrderResponse.java` - Empty (not used)

**Current Implementation**: Services use Entity classes directly in API requests/responses.

**Recommendation**: Consider implementing DTOs to:
- Separate API contracts from database entities
- Hide sensitive fields (e.g., password in User)
- Add validation and transformation logic
- Version APIs independently
