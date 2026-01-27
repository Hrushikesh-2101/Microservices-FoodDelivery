import { Component, OnInit } from '@angular/core';
import { ProductService, ProductPage } from '../../services/product.service';
import { OrderService, Order } from '../../services/order.service';
import { CartService } from '../../services/cart.service';
import { AuthService } from '../../services/auth.service';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  featuredProducts: ProductPage | null = null;
  recentOrders: Order[] = [];
  cartItemCount: number = 0;
  totalCartValue: number = 0;
  currentUser: User | null = null;

  constructor(
    private productService: ProductService,
    private orderService: OrderService,
    private cartService: CartService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    this.loadFeaturedProducts();
    this.loadRecentOrders();
    this.updateCartStats();
    
    this.cartService.cart$.subscribe(() => {
      this.updateCartStats();
    });
  }

  loadFeaturedProducts(): void {
    this.productService.getAllProducts(0, 6).subscribe(
      data => this.featuredProducts = data,
      error => console.error('Error loading featured products:', error)
    );
  }

  loadRecentOrders(): void {
    this.orderService.getAllOrders().subscribe(
      data => this.recentOrders = data.slice(0, 5),
      error => console.error('Error loading recent orders:', error)
    );
  }

  updateCartStats(): void {
    this.cartItemCount = this.cartService.getCartItemCount();
    this.totalCartValue = this.cartService.getTotalPrice();
  }
}

