import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  isLoading = false;
  hidePassword = true;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    // TODO: Implement auth check later
    // if (this.authService.isAuthenticated()) {
    //   this.router.navigate(['/dashboard']);
    //   return;
    // }

    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  onSubmit(): void {
    if (this.loginForm.invalid) {
      return;
    }

    this.isLoading = true;

    this.authService.login(this.loginForm.value).subscribe({
      next: (response) => {
        console.log('Login response:', response);
        this.isLoading = false;
        // On success response, sign in and navigate to dashboard
        this.snackBar.open('Login successful!', 'Close', { duration: 3000 });
        this.router.navigate(['/dashboard']).then(
          (success) => {
            if (!success) {
              console.error('Navigation to dashboard failed');
            }
          }
        );
      },
      error: (error) => {
        console.error('Login error:', error);
        this.isLoading = false;
        // Check if error has a response body (might be a successful login with error status)
        if (error.error && (error.error.token || error.error.message === 'Login successful' || error.status === 200)) {
          // API returned success but with error status code
          // Manually trigger auth state update
          this.authService.setAuthStateFromResponse(error.error);
          this.snackBar.open('Login successful!', 'Close', { duration: 3000 });
          this.router.navigate(['/dashboard']).then(
            (success) => {
              if (!success) {
                console.error('Navigation to dashboard failed');
              }
            }
          );
        } else {
          const message = error.error?.message || error.message || 'Invalid email or password';
          this.snackBar.open(message, 'Close', { duration: 3000 });
        }
      }
    });
  }

  get email() {
    return this.loginForm.get('email');
  }

  get password() {
    return this.loginForm.get('password');
  }
}
