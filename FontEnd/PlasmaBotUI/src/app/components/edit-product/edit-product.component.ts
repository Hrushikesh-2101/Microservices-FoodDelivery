import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.css']
})
export class EditProductComponent implements OnInit {
  form!: FormGroup;
  productId: number | null = null;
  isLoading = false;
  loadingProduct = true;

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    private route: ActivatedRoute,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      name: ['', [Validators.required]],
      description: [''],
      price: [null, [Validators.required, Validators.min(0.01)]],
      available: [true]
    });

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.productId = parseInt(id, 10);
      this.loadProduct(this.productId);
    } else {
      this.loadingProduct = false;
      this.snackBar.open('Invalid product', 'Close', { duration: 3000 });
      this.router.navigate(['/products']);
    }
  }

  loadProduct(id: number): void {
    this.productService.getProductById(id).subscribe({
      next: (product) => {
        this.form.patchValue({
          name: product.name,
          description: product.description || '',
          price: product.price,
          available: product.available
        });
        this.loadingProduct = false;
      },
      error: () => {
        this.loadingProduct = false;
        this.snackBar.open('Product not found', 'Close', { duration: 3000 });
        this.router.navigate(['/products']);
      }
    });
  }

  onSubmit(): void {
    if (this.form.invalid || this.productId == null) {
      return;
    }

    this.isLoading = true;
    const value = this.form.value;

    this.productService.updateProduct(this.productId, {
      name: value.name,
      description: value.description || undefined,
      price: parseFloat(value.price),
      available: value.available
    }).subscribe({
      next: (product) => {
        this.isLoading = false;
        this.snackBar.open(`Product "${product.name}" updated successfully`, 'Close', { duration: 3000 });
        this.router.navigate(['/products', product.id]);
      },
      error: (err) => {
        this.isLoading = false;
        const message = err.error?.message || err.error?.error || 'Failed to update product. Try again.';
        this.snackBar.open(message, 'Close', { duration: 4000 });
      }
    });
  }

  cancel(): void {
    if (this.productId != null) {
      this.router.navigate(['/products', this.productId]);
    } else {
      this.router.navigate(['/products']);
    }
  }

  get name() { return this.form.get('name'); }
  get description() { return this.form.get('description'); }
  get price() { return this.form.get('price'); }
}
