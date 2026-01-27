import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService, Product } from '../../services/product.service';
import { CartService } from '../../services/cart.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {
  product: Product | null = null;
  quantity: number = 1;
  loading: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private productService: ProductService,
    private cartService: CartService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.loadProduct(parseInt(id));
    }
  }

  loadProduct(id: number): void {
    this.loading = true;
    this.productService.getProductById(id).subscribe(
      product => {
        this.product = product;
        this.loading = false;
      },
      error => {
        console.error('Error loading product:', error);
        this.snackBar.open('Product not found', 'Close', { duration: 3000 });
        this.loading = false;
        this.router.navigate(['/products']);
      }
    );
  }

  goBack(): void {
    this.router.navigate(['/products']);
  }

  addToCart(): void {
    if (this.product && this.product.available) {
      this.cartService.addToCart(this.product, this.quantity);
      this.snackBar.open(`${this.product.name} added to cart`, 'Close', { duration: 2000 });
    } else {
      this.snackBar.open('Product is not available', 'Close', { duration: 2000 });
    }
  }

  increaseQuantity(): void {
    this.quantity++;
  }

  decreaseQuantity(): void {
    if (this.quantity > 1) {
      this.quantity--;
    }
  }
}

