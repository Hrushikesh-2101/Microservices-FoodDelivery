# Food Delivery System - Angular UI

A modern Angular Material-based user interface for the Food Delivery Microservices system.

## Features

- **Dashboard**: Overview of cart, orders, and featured products
- **Products**: Browse and search products with advanced filtering
- **Product Details**: View detailed product information
- **Shopping Cart**: Manage cart items and quantities
- **Orders**: View and manage orders
- **Responsive Design**: Works on desktop and mobile devices

## Prerequisites

- Node.js v22.14.0 (or compatible)
- Angular CLI 10.2.4 (or compatible)
- Angular Material 11.2.13

## Installation

1. Navigate to the project directory:
```bash
cd FontEnd/PlasmaBotUI
```

2. Install dependencies:
```bash
npm install
```

3. Install Angular Material (if not already installed):
```bash
npm install @angular/material@^11.2.13 @angular/cdk@^11.2.13 --legacy-peer-deps
```

## Configuration

Update the API endpoints in `src/environments/environment.ts` if your backend services are running on different ports:

```typescript
export const environment = {
  production: false,
  apiUrl: {
    productService: 'http://localhost:8082/api/products',
    orderService: 'http://localhost:8083/orders',
    userService: 'http://localhost:8081/api/users'
  }
};
```

## Running the Application

1. Make sure all backend services are running:
   - User Service: http://localhost:8081
   - Product Service: http://localhost:8082
   - Order Service: http://localhost:8083

2. Start the Angular development server:
```bash
ng serve
```

3. Open your browser and navigate to:
```
http://localhost:4200
```

## Project Structure

```
src/app/
├── components/
│   ├── dashboard/          # Dashboard component
│   ├── products/           # Product listing component
│   ├── product-details/    # Product details component
│   ├── cart/              # Shopping cart component
│   ├── orders/            # Orders management component
│   └── order-dialog/      # Order placement dialog
├── services/
│   ├── product.service.ts # Product API service
│   ├── order.service.ts   # Order API service
│   └── cart.service.ts    # Cart management service
├── app.component.*        # Main app component
├── app.module.ts          # App module with all imports
└── app-routing.module.ts  # Routing configuration
```

## Key Features

### Products Page
- Search products by name
- Filter by price range
- Filter by availability
- Pagination support
- Add products to cart

### Shopping Cart
- View cart items
- Update quantities
- Remove items
- Calculate total
- Checkout functionality

### Orders
- View all orders
- Order status display
- Delete orders
- Order details

### Dashboard
- Cart statistics
- Recent orders
- Featured products

## Angular Material Components Used

- MatToolbar
- MatSidenav
- MatCard
- MatButton
- MatIcon
- MatTable
- MatPaginator
- MatDialog
- MatSnackBar
- MatFormField
- MatInput
- MatSelect
- MatChip
- MatBadge
- MatProgressSpinner
- MatTabs
- MatStepper
- MatCheckbox
- MatSlider
- MatTooltip

## Development

### Build for Production
```bash
ng build --prod
```

### Run Tests
```bash
ng test
```

### Lint
```bash
ng lint
```

## Troubleshooting

1. **CORS Issues**: Make sure your backend services have CORS enabled to allow requests from `http://localhost:4200`

2. **API Connection Errors**: Verify that all backend services are running and accessible

3. **Material Icons Not Showing**: Ensure `@angular/material` is properly installed

4. **Routing Issues**: Check that all routes are properly configured in `app-routing.module.ts`

## Notes

- The application uses Angular 11.2.14 with Angular Material 11.2.13
- Cart data is stored in browser localStorage
- The UI is fully responsive and follows Material Design principles

