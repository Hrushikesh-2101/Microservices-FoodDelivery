# Microservices-FoodDelivery

A comprehensive microservices-based food delivery system built with Spring Boot, following a 16-week learning roadmap. This project demonstrates various microservices patterns, best practices, and production-ready features.

## 📋 Project Overview

This project implements a food delivery system using microservices architecture with three core services:
- **User Service** (Port 8081) - Manages user accounts and authentication
- **Product Service** (Port 8082) - Manages products, menus, and categories
- **Order Service** (Port 8083) - Manages orders and order processing

## 🗺️ Microservices Learning Roadmap

This roadmap guides you through building a microservices-based system using a hands-on, project-based approach. It spans 16 weeks across four phases, covering foundational concepts, advanced patterns, observability, and deployment.

**Target Audience:** Developers aiming to master microservices architecture with Spring Boot and related technologies.

---

## 🟢 Phase 1: Microservices Foundation (Weeks 1-4)

### ✅ Step 1: Setting Up the Project

- **Status:** ✅ **COMPLETED**
- **Task:** Create a Spring Boot multi-module project.
- **Implementation:**
    - ✅ Built `User Service` and `Product Service` as separate microservices.
    - ✅ Using Spring Boot 2.7.18, Spring Data JPA, and MySQL database.
- **Key Concepts:**
    - What are microservices?
    - Monolith vs. Microservices.

### ✅ Step 2: Splitting into Microservices

- **Status:** ✅ **COMPLETED**
- **Task:** Introduce `Order Service`, independent of user/product services.
- **Implementation:**
    - ✅ Implemented Order Service as a separate microservice.
    - ⚠️ **Note:** Currently using shared database. Database-per-service architecture needs to be implemented.
- **Key Concepts:**
    - Domain-Driven Design (DDD).
    - Loose Coupling, High Cohesion.

### ⏳ Step 3: Service Discovery & Configuration

- **Status:** ⏳ **PENDING**
- **Task:** Set up service discovery and centralized configuration.
- **Implementation:**
    - ⏳ Use Eureka Server for service discovery.
    - ⏳ Centralize configurations with Spring Cloud Config.
- **Key Concepts:**
    - Service Registration.
    - Centralized Config Management.

### ⏳ Step 4: Inter-Service Communication (Sync + Async)

- **Status:** ⏳ **PENDING**
- **Task:** Enable communication between services.
- **Implementation:**
    - ⏳ Use Feign Client for RESTful synchronous communication.
    - ⏳ Introduce event-driven architecture with RabbitMQ for asynchronous communication.
- **Key Concepts:**
    - Feign Client.
    - Eventual Consistency.
    - Message Brokers.

---

## 🔵 Phase 2: API Gateway, Security & Resilience (Weeks 5-8)

### ⏳ Step 5: API Gateway & Load Balancing

- **Status:** ⏳ **PENDING**
- **Task:** Implement centralized routing and load balancing.
- **Implementation:**
    - ⏳ Use Spring Cloud Gateway for routing.
    - ⏳ Apply client-side load balancing with Spring Cloud LoadBalancer.
- **Key Concepts:**
    - API Gateway vs. Direct Calls.
    - Load Balancing Strategies.

### ⏳ Step 6: Security & Authentication (JWT, OAuth2)

- **Status:** ⏳ **PENDING**
- **Task:** Secure the microservices.
- **Implementation:**
    - ⏳ Secure APIs with Spring Security and JWT.
    - ⏳ Set up an OAuth2 authorization server (e.g., Okta or Keycloak).
- **Key Concepts:**
    - Authentication, Authorization.
    - OAuth2 Flows.

### ⏳ Step 7: Circuit Breakers & Rate Limiting

- **Status:** ⏳ **PENDING**
- **Task:** Enhance fault tolerance and control traffic.
- **Implementation:**
    - ⏳ Use Resilience4j Circuit Breaker for fault tolerance.
    - ⏳ Implement Rate Limiting with Redis in the API Gateway.
- **Key Concepts:**
    - Circuit Breaker Pattern.
    - API Rate Limiting.

### ⏳ Step 8: Data Management & Eventual Consistency

- **Status:** ⏳ **PENDING**
- **Task:** Manage distributed transactions and reliable events.
- **Implementation:**
    - ⏳ Implement the Saga Pattern for distributed transactions.
    - ⏳ Use the Outbox Pattern for reliable event delivery.
- **Key Concepts:**
    - CAP Theorem.
    - Saga vs. Two-Phase Commit (2PC).
    - Outbox Pattern.

---

## 🟠 Phase 3: Observability & Performance (Weeks 9-12)

### ⏳ Step 9: Distributed Tracing with Sleuth & Zipkin

- **Status:** ⏳ **PENDING**
- **Task:** Enable request tracing across services.
- **Implementation:**
    - ⏳ Integrate Spring Cloud Sleuth and Zipkin for distributed tracing.
- **Key Concepts:**
    - Observability.
    - Tracing Headers, Correlation ID.

### ⏳ Step 10: Centralized Logging with ELK Stack

- **Status:** ⏳ **PENDING**
- **Task:** Aggregate and analyze logs.
- **Implementation:**
    - ⏳ Configure Logstash, Elasticsearch, and Kibana (ELK) for centralized logging.
- **Key Concepts:**
    - Structured Logging.
    - Log Aggregation, Log Queries.

### ⏳ Step 11: Metrics & Monitoring (Prometheus & Grafana)

- **Status:** ⏳ **PENDING**
- **Task:** Monitor service health and performance.
- **Implementation:**
    - ⏳ Use Spring Boot Actuator for health monitoring.
    - ⏳ Integrate Prometheus and Grafana for metrics visualization.
- **Key Concepts:**
    - Health Checks.
    - Prometheus Querying.
    - Grafana Dashboards.

### ⏳ Step 12: Caching Strategies

- **Status:** ⏳ **PENDING**
- **Task:** Optimize performance with caching.
- **Implementation:**
    - ⏳ Implement Redis caching for frequently accessed data.
- **Key Concepts:**
    - Cache Aside Pattern.
    - Distributed Caching.

---

## 🟣 Phase 4: Testing & Deployment (Weeks 13-16)

### ⏳ Step 13: Microservices Testing Strategy

- **Status:** ⚠️ **PARTIAL** (Basic tests exist)
- **Task:** Ensure reliability through testing.
- **Implementation:**
    - ✅ Basic unit tests exist (JUnit + Mockito).
    - ⏳ Integration Testing: Spring Boot Test + Testcontainers.
    - ⏳ Contract Testing: Spring Cloud Contract for API compatibility.
- **Key Concepts:**
    - Consumer-Driven Contracts.
    - Integration Testing Best Practices.

### ⏳ Step 14: Polyglot Persistence & Database Scaling

- **Status:** ⏳ **PENDING**
- **Task:** Use multiple database types.
- **Implementation:**
    - ⏳ Use MongoDB for Product Service, PostgreSQL for User Service.
- **Key Concepts:**
    - SQL vs. NoSQL.
    - Polyglot Persistence Best Practices.

### ⏳ Step 15: Kubernetes for Orchestration

- **Status:** ⏳ **PENDING**
- **Task:** Deploy microservices to Kubernetes.
- **Implementation:**
    - ⏳ Deploy using Kubernetes (K8s).
    - ⏳ Write YAML manifests for Deployments and Services.
- **Key Concepts:**
    - Kubernetes Pods, Services, Deployments, ConfigMaps.

### ⏳ Step 16: CI/CD Pipeline for Automated Deployment

- **Status:** ⏳ **PENDING**
- **Task:** Automate the build and deployment process.
- **Implementation:**
    - ⏳ Set up GitHub Actions or Jenkins for CI/CD.
    - ⏳ Automate Docker builds and push to Docker Hub.
    - ⏳ Deploy to Kubernetes with Helm Charts.
- **Key Concepts:**
    - CI/CD Pipelines.
    - Docker Image Versioning.
    - Helm Charts.

---

## 🏗️ Current Architecture

### Services

1. **User Service** (`user-service`)
   - Port: 8081
   - Database: MySQL (shared - needs to be separated)
   - Responsibilities: User management, authentication

2. **Product Service** (`product-service`)
   - Port: 8082
   - Database: MySQL (shared - needs to be separated)
   - Responsibilities: Product catalog, menu management, categories

3. **Order Service** (`order-service`)
   - Port: 8083
   - Database: MySQL (shared - needs to be separated)
   - Responsibilities: Order processing, order status management

### Technology Stack

- **Framework:** Spring Boot 2.7.18
- **Database:** MySQL 8.0
- **ORM:** Spring Data JPA / Hibernate
- **Build Tool:** Maven
- **Java Version:** 1.8

---

## 🚀 Getting Started

### Prerequisites

- Java 8 or higher
- Maven 3.6+
- MySQL 8.0+
- Docker (for future containerization)

### Database Setup

1. Create MySQL database:
```sql
CREATE DATABASE food_delivery_system;
```

2. Update `application.properties` in each service with your database credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/food_delivery_system
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Running the Services

1. **User Service:**
```bash
cd user-service/user-service
mvn spring-boot:run
```

2. **Product Service:**
```bash
cd product-service/product-service
mvn spring-boot:run
```

3. **Order Service:**
```bash
cd order-service/order-service
mvn spring-boot:run
```

### API Endpoints

#### User Service (Port 8081)
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `POST /api/users` - Create new user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

#### Product Service (Port 8082)
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product

#### Order Service (Port 8083)
- `GET /api/orders` - Get all orders
- `GET /api/orders/{id}` - Get order by ID
- `POST /api/orders` - Create new order
- `PUT /api/orders/{id}` - Update order
- `DELETE /api/orders/{id}` - Delete order

---

## 📝 Next Steps

### Immediate Priorities

1. **Implement Database-per-Service** (Step 2 completion)
   - Separate databases for each service
   - Update connection strings

2. **Service Discovery** (Step 3)
   - Set up Eureka Server
   - Register all services with Eureka

3. **Inter-Service Communication** (Step 4)
   - Implement Feign Client for synchronous calls
   - Set up RabbitMQ for asynchronous messaging

4. **API Gateway** (Step 5)
   - Implement Spring Cloud Gateway
   - Configure routing rules

---

## 📚 Learning Resources

- [Spring Cloud Documentation](https://spring.io/projects/spring-cloud)
- [Microservices Patterns](https://microservices.io/patterns/)
- [Domain-Driven Design](https://martinfowler.com/bliki/DomainDrivenDesign.html)

---

## 🤝 Contributing

This is a learning project. Feel free to:
- Implement missing features from the roadmap
- Improve existing code
- Add documentation
- Report issues

---

## 📄 License

This project is for educational purposes.

---

## 📊 Progress Summary

- **Completed:** 2/16 steps (12.5%)
- **In Progress:** 0/16 steps
- **Pending:** 14/16 steps (87.5%)

**Legend:**
- ✅ Completed
- ⏳ Pending
- ⚠️ Partial/Needs Improvement
