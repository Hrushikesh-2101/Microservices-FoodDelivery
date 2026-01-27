import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CartItem } from '../../services/cart.service';

export interface OrderDialogData {
  totalAmount: number;
  items: CartItem[];
}

@Component({
  selector: 'app-order-dialog',
  templateUrl: './order-dialog.component.html',
  styleUrls: ['./order-dialog.component.css']
})
export class OrderDialogComponent {
  orderForm: FormGroup;

  constructor(
    public dialogRef: MatDialogRef<OrderDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: OrderDialogData,
    private fb: FormBuilder
  ) {
    this.orderForm = this.fb.group({
      userId: [1, Validators.required],
      address: ['', Validators.required],
      phone: ['', Validators.required]
    });
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  onSubmit(): void {
    if (this.orderForm.valid) {
      this.dialogRef.close(this.orderForm.value);
    }
  }
}

