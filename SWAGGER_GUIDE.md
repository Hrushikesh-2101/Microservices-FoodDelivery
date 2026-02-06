# How to Check Available Endpoints Using Swagger

Swagger UI provides an interactive interface to explore and test all available API endpoints in your microservices.

## Accessing Swagger UI

After starting each service, access Swagger UI using the following URLs:

### Service URLs:

1. **User Service**
   - Swagger UI: http://localhost:8081/swagger-ui.html
   - API Docs (JSON): http://localhost:8081/api-docs

2. **Product Service**
   - Swagger UI: http://localhost:8082/swagger-ui.html
   - API Docs (JSON): http://localhost:8082/api-docs

3. **Order Service**
   - Swagger UI: http://localhost:8083/swagger-ui.html
   - API Docs (JSON): http://localhost:8083/api-docs

## How to Use Swagger UI

### Step 1: Start Your Services

Start the services using one of these methods:

**Option A: Using Cursor/VS Code Run & Debug**
- Press `F5` → Select "Run All Services" → Press `F5` again

**Option B: Using Terminal**
```bash
# Terminal 1 - User Service
cd user-service/user-service
mvn spring-boot:run

# Terminal 2 - Product Service
cd product-service/product-service
mvn spring-boot:run

# Terminal 3 - Order Service
cd order-service/order-service
mvn spring-boot:run
```

### Step 2: Open Swagger UI

1. Open your web browser
2. Navigate to one of the Swagger UI URLs above (e.g., http://localhost:8081/swagger-ui.html)
3. You'll see the Swagger UI interface with all available endpoints

## Understanding Swagger UI Interface

### Main Components:

1. **API Information Section** (Top)
   - Shows API title, version, description
   - Displays contact information

2. **Tags/Endpoints List** (Left Side)
   - Groups endpoints by controller (e.g., "Order Controller", "Product Controller")
   - Click on a tag to expand and see all endpoints in that group

3. **Endpoint Details** (Main Area)
   - Each endpoint shows:
     - **HTTP Method** (GET, POST, PUT, DELETE)
     - **Endpoint Path** (e.g., `/orders`, `/api/products/{id}`)
     - **Description** of what the endpoint does
     - **Parameters** (path, query, body parameters)
     - **Request Body** schema (for POST/PUT)
     - **Response** schemas and status codes

### How to Explore Endpoints:

1. **View All Endpoints:**
   - Scroll through the list on the left
   - All endpoints are organized by controller/tag

2. **Expand an Endpoint:**
   - Click on any endpoint to expand it
   - See detailed information about parameters, request body, and responses

3. **View Request/Response Models:**
   - Click on "Schemas" section at the bottom
   - See the structure of request and response objects
   - Understand what fields are required/optional

4. **Test an Endpoint:**
   - Click "Try it out" button on any endpoint
   - Fill in the parameters
   - Click "Execute"
   - See the actual response from your service

## Example: Exploring Product Service Endpoints

1. Open: http://localhost:8082/swagger-ui.html

2. You'll see endpoints grouped under "Product Controller":
   - `GET /api/products` - Get all products
   - `GET /api/products/{id}` - Get product by ID
   - `POST /api/products` - Create a new product
   - `PUT /api/products/{id}` - Update a product
   - `DELETE /api/products/{id}` - Delete a product
   - `GET /api/products/filterByName` - Filter by name
   - `GET /api/products/filterByPrice` - Filter by price range
   - `GET /api/products/filterByAvailability` - Filter by availability
   - `GET /api/products/search` - Advanced search

3. Click on any endpoint to see:
   - Required/optional parameters
   - Example values
   - Response codes (200, 404, etc.)
   - Response body structure

## Testing Endpoints in Swagger UI

### Example: Testing GET /api/products/{id}

1. Click on `GET /api/products/{id}` to expand it
2. Click the **"Try it out"** button
3. Enter a product ID (e.g., `1`) in the `id` field
4. Click **"Execute"**
5. View the response:
   - **Response Code**: 200 (Success) or 404 (Not Found)
   - **Response Body**: JSON with product details
   - **Response Headers**: Content-Type, etc.

### Example: Testing POST /api/products

1. Click on `POST /api/products` to expand it
2. Click **"Try it out"**
3. In the Request Body section, you'll see a JSON template
4. Modify the JSON with your product data:
   ```json
   {
     "name": "New Pizza",
     "price": 12.99,
     "description": "Delicious pizza",
     "available": true
   }
   ```
5. Click **"Execute"**
6. See the created product in the response

## Key Features of Swagger UI

### 1. **Interactive API Documentation**
   - See all endpoints in one place
   - Understand request/response formats
   - View example values

### 2. **Try It Out**
   - Test endpoints directly from the browser
   - No need for Postman or curl commands
   - See real responses from your services

### 3. **Schema Documentation**
   - View data models (Product, Order, User)
   - Understand field types and constraints
   - See required vs optional fields

### 4. **Response Codes**
   - See all possible HTTP response codes
   - Understand error scenarios
   - View example error responses

## Tips for Using Swagger UI

1. **Use Filters**: If you have many endpoints, use the search/filter box to find specific endpoints

2. **Check Schemas**: Always check the "Schemas" section to understand the data structure

3. **Read Descriptions**: Each endpoint has a description explaining what it does

4. **Test Different Scenarios**: 
   - Test with valid data
   - Test with invalid data to see error responses
   - Test edge cases

5. **Copy Examples**: You can copy the curl command from Swagger UI to use in other tools

## Alternative: View Raw API Documentation

If you prefer JSON format, you can access the raw OpenAPI specification:

- User Service: http://localhost:8081/api-docs
- Product Service: http://localhost:8082/api-docs
- Order Service: http://localhost:8083/api-docs

This returns the OpenAPI 3.0 specification in JSON format, which can be imported into other API tools like Postman.

## Troubleshooting

### Swagger UI Not Loading?
- Ensure the service is running
- Check the port number matches (8081, 8082, or 8083)
- Verify the URL is correct: `http://localhost:PORT/swagger-ui.html`

### Endpoints Not Showing?
- Make sure the service started successfully
- Check application logs for errors
- Verify Swagger dependencies are in `pom.xml`

### Can't Execute Requests?
- Ensure the service is running and accessible
- Check if the database is connected
- Verify request body format matches the schema

## Summary

Swagger UI is your one-stop interface to:
- ✅ View all available endpoints
- ✅ Understand request/response formats
- ✅ Test endpoints interactively
- ✅ View data models and schemas
- ✅ See example requests and responses

Simply start your services and navigate to the Swagger UI URL for each service!

