import { Component, OnInit } from '@angular/core';
import { OrderService, Order } from '../../services/order.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {
  orders: Order[] = [];
  displayedColumns: string[] = ['id', 'orderDate', 'totalAmount', 'status', 'actions'];
  dataSource = new MatTableDataSource<Order>(this.orders);
  loading: boolean = false;

  constructor(
    private orderService: OrderService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.loadOrders();
  }

  loadOrders(): void {
    this.loading = true;
    this.orderService.getAllOrders().subscribe(
      orders => {
        this.orders = orders;
        this.dataSource.data = orders;
        this.loading = false;
      },
      error => {
        console.error('Error loading orders:', error);
        this.snackBar.open('Error loading orders', 'Close', { duration: 3000 });
        this.loading = false;
      }
    );
  }

  getStatusColor(status?: string): 'primary' | 'accent' | 'warn' {
    if (!status) return 'primary';
    switch (status.toUpperCase()) {
      case 'PENDING':
        return 'warn';
      case 'COMPLETED':
        return 'primary';
      case 'CANCELLED':
        return 'warn';
      default:
        return 'primary';
    }
  }

  viewOrder(order: Order): void {
    // Implement order details view
    console.log('View order:', order);
  }

  deleteOrder(order: Order): void {
    if (order.id && confirm('Are you sure you want to delete this order?')) {
      this.orderService.deleteOrder(order.id).subscribe(
        () => {
          this.snackBar.open('Order deleted successfully', 'Close', { duration: 2000 });
          this.loadOrders();
        },
        error => {
          console.error('Error deleting order:', error);
          this.snackBar.open('Error deleting order', 'Close', { duration: 3000 });
        }
      );
    }
  }
}
