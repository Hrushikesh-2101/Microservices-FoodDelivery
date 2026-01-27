import { Component, OnInit } from '@angular/core';
import { CartService, CartItem } from '../../services/cart.service';
import { OrderService, Order, OrderItem } from '../../services/order.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { OrderDialogComponent } from '../order-dialog/order-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  cartItems: CartItem[] = [];
  totalPrice: number = 0;

  constructor(
    public cartService: CartService,
    private orderService: OrderService,
    private snackBar: MatSnackBar,
    private router: Router,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.loadCart();
    this.cartService.cart$.subscribe(() => {
      this.loadCart();
    });
  }

  loadCart(): void {
    this.cartItems = this.cartService.getCartItems();
    this.totalPrice = this.cartService.getTotalPrice();
  }

  updateQuantity(productId: number, event: any): void {
    const quantity = parseInt(event.target.value);
    this.cartService.updateQuantity(productId, quantity);
  }

  removeItem(productId: number): void {
    this.cartService.removeFromCart(productId);
    this.snackBar.open('Item removed from cart', 'Close', { duration: 2000 });
  }

  checkout(): void {
    if (this.cartItems.length === 0) {
      this.snackBar.open('Cart is empty', 'Close', { duration: 2000 });
      return;
    }

    const dialogRef = this.dialog.open(OrderDialogComponent, {
      width: '500px',
      data: {
        totalAmount: this.totalPrice,
        items: this.cartItems
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.createOrder(result);
      }
    });
  }

  createOrder(orderData: any): void {
    const orderItems: OrderItem[] = this.cartItems.map(item => ({
      productId: item.product.id!,
      quantity: item.quantity,
      price: item.product.price
    }));

    const order: Order = {
      userId: orderData.userId || 1,
      totalAmount: this.totalPrice,
      status: 'PENDING',
      items: orderItems
    };

    this.orderService.createOrder(order).subscribe(
      createdOrder => {
        this.snackBar.open('Order placed successfully!', 'Close', { duration: 3000 });
        this.cartService.clearCart();
        this.router.navigate(['/orders']);
      },
      error => {
        console.error('Error creating order:', error);
        this.snackBar.open('Error placing order', 'Close', { duration: 3000 });
      }
    );
  }
}

