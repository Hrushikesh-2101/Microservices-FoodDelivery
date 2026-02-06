import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {
  form!: FormGroup;
  isLoading = false;

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
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
  }

  onSubmit(): void {
    if (this.form.invalid) {
      return;
    }

    this.isLoading = true;
    const value = this.form.value;

    this.productService.createProduct({
      name: value.name,
      description: value.description || undefined,
      price: parseFloat(value.price),
      available: value.available
    }).subscribe({
      next: (product) => {
        this.isLoading = false;
        this.snackBar.open(`Product "${product.name}" created successfully`, 'Close', { duration: 3000 });
        this.router.navigate(['/products']);
      },
      error: (err) => {
        this.isLoading = false;
        const message = err.error?.message || err.error?.error || 'Failed to create product. Try again.';
        this.snackBar.open(message, 'Close', { duration: 4000 });
      }
    });
  }

  cancel(): void {
    this.router.navigate(['/products']);
  }

  get name() { return this.form.get('name'); }
  get description() { return this.form.get('description'); }
  get price() { return this.form.get('price'); }
}
