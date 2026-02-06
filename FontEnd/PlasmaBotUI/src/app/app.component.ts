import { Component, OnInit, OnDestroy } from '@angular/core';
import { CartService } from './services/cart.service';
import { AuthService } from './services/auth.service';
import { Observable, Subscription } from 'rxjs';
import { map } from 'rxjs/operators';
import { User } from './models/user.model';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'Food Delivery System';
  cartItemCount$: Observable<number> = new Observable();
  currentUser: User | null = null;
  isAuthenticated = false;
  private subscriptions: Subscription[] = [];

  constructor(
    private cartService: CartService,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.cartItemCount$ = this.cartService.cart$.pipe(
      map(() => this.cartService.getCartItemCount())
    );

    // TODO: Auth removed temporarily - always show UI
    // Set authenticated to true so UI is always visible
    this.isAuthenticated = true;

    // Subscribe to current user changes (for display purposes)
    const userSub = this.authService.currentUser$.subscribe(user => {
      this.currentUser = user;
    });
    this.subscriptions.push(userSub);

    // Initial user check
    this.currentUser = this.authService.getCurrentUser();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  logout(): void {
    this.authService.logout();
  }
}
