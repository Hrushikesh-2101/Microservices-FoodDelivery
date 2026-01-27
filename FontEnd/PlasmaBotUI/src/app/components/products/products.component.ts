import { Component, OnInit, ViewChild } from '@angular/core';
import { ProductService, Product, ProductPage } from '../../services/product.service';
import { CartService } from '../../services/cart.service';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  products: Product[] = [];
  totalElements: number = 0;
  pageSize: number = 12;
  pageIndex: number = 0;
  
  // Filters
  searchName: string = '';
  minPrice: number = 0;
  maxPrice: number = 1000;
  availableOnly: boolean = false;
  
  loading: boolean = false;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private productService: ProductService,
    private cartService: CartService,
    private snackBar: MatSnackBar,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.loading = true;
    
    const filters: any = {};
    if (this.searchName) filters.name = this.searchName;
    if (this.minPrice > 0) filters.minPrice = this.minPrice;
    if (this.maxPrice < 1000) filters.maxPrice = this.maxPrice;
    if (this.availableOnly) filters.available = true;

    this.productService.searchProducts(filters, this.pageIndex, this.pageSize).subscribe(
      (data: ProductPage) => {
        this.products = data.content;
        this.totalElements = data.totalElements;
        this.loading = false;
      },
      error => {
        console.error('Error loading products:', error);
        this.snackBar.open('Error loading products', 'Close', { duration: 3000 });
        this.loading = false;
      }
    );
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadProducts();
  }

  onSearch(): void {
    this.pageIndex = 0;
    this.loadProducts();
  }

  onFilterChange(): void {
    this.pageIndex = 0;
    this.loadProducts();
  }

  addToCart(product: Product): void {
    if (product.available) {
      this.cartService.addToCart(product, 1);
      this.snackBar.open(`${product.name} added to cart`, 'Close', { duration: 2000 });
    } else {
      this.snackBar.open('Product is not available', 'Close', { duration: 2000 });
    }
  }

  viewDetails(product: Product): void {
    if (product.id) {
      this.router.navigate(['/products', product.id]);
    }
  }
}

