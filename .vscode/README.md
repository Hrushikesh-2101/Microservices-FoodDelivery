# Running Spring Boot Services in Cursor/VS Code

This directory contains configuration files to run and debug Spring Boot microservices directly from Cursor/VS Code.

## Prerequisites

1. **Java Extension Pack** - Install the Java Extension Pack in Cursor/VS Code:
   - Open Extensions (Ctrl+Shift+X)
   - Search for "Extension Pack for Java" by Microsoft
   - Install it

2. **Maven** - Ensure Maven is installed and available in your PATH
   - Verify: Run `mvn -version` in terminal

3. **Java JDK** - Ensure Java 8+ is installed
   - Verify: Run `java -version` in terminal

## How to Run Services

### Method 1: Using Run and Debug Panel (Recommended)

1. Open the **Run and Debug** panel (Ctrl+Shift+D or F5)
2. Select one of the following configurations from the dropdown:
   - **Run User Service** - Runs user-service on port 8081
   - **Run Product Service** - Runs product-service on port 8082
   - **Run Order Service** - Runs order-service on port 8083
   - **Run All Services** - Runs all three services simultaneously
3. Click the green play button or press F5

### Method 2: Using Maven Tasks

1. Open the **Terminal** menu → **Run Task** (Ctrl+Shift+P → "Tasks: Run Task")
2. Select one of the Maven tasks:
   - **Run User Service (Maven)**
   - **Run Product Service (Maven)**
   - **Run Order Service (Maven)**

### Method 3: Using Terminal

Navigate to each service directory and run:

```bash
# User Service
cd user-service/user-service
mvn spring-boot:run

# Product Service (in a new terminal)
cd product-service/product-service
mvn spring-boot:run

# Order Service (in a new terminal)
cd order-service/order-service
mvn spring-boot:run
```

## Debugging Services

1. Open the **Run and Debug** panel (Ctrl+Shift+D)
2. Select a debug configuration:
   - **Debug User Service**
   - **Debug Product Service**
   - **Debug Order Service**
3. Set breakpoints in your code
4. Click the green play button or press F5
5. The debugger will stop at your breakpoints

## Building Services

1. Open the **Terminal** menu → **Run Task** (Ctrl+Shift+P → "Tasks: Run Task")
2. Select:
   - **Build User Service** - Builds only user-service
   - **Build Product Service** - Builds only product-service
   - **Build Order Service** - Builds only order-service
   - **Build All Services** - Builds all services in parallel

Or use the default build task: **Ctrl+Shift+B**

## Service URLs

After starting the services, access them at:

- **User Service**: http://localhost:8081
  - Swagger UI: http://localhost:8081/swagger-ui.html
  - API Docs: http://localhost:8081/api-docs

- **Product Service**: http://localhost:8082
  - Swagger UI: http://localhost:8082/swagger-ui.html
  - API Docs: http://localhost:8082/api-docs

- **Order Service**: http://localhost:8083
  - Swagger UI: http://localhost:8083/swagger-ui.html
  - API Docs: http://localhost:8083/api-docs

## Troubleshooting

### Java Extension Not Working
- Ensure Java Extension Pack is installed
- Reload Cursor/VS Code window (Ctrl+Shift+P → "Developer: Reload Window")
- Check Java home path in settings

### Maven Not Found
- Ensure Maven is installed and in PATH
- Update `maven.executable.path` in `.vscode/settings.json` if needed

### Port Already in Use
- Stop any services running on ports 8081, 8082, or 8083
- Or change the port in `application.properties` files

### Build Errors
- Run `mvn clean install` in each service directory
- Ensure all dependencies are downloaded
- Check Java version compatibility (Java 8 required)

