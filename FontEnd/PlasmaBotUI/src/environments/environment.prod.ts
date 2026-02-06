export const environment = {
  production: true,
  apiUrl: {
    // All services go through API Gateway
    apiGateway: 'http://localhost:8080',
    productService: 'http://localhost:8080/api/products',
    orderService: 'http://localhost:8080/orders',
    userService: 'http://localhost:8080/api/users',
    authService: 'http://localhost:8080/api/auth'
  }
};
