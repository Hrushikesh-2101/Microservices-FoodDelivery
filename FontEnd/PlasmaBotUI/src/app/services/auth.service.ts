import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { User, LoginRequest, RegisterRequest, AuthResponse } from '../models/user.model';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  // Authentication endpoints are now handled by API Gateway
  private apiUrl = environment.apiUrl.userService;
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  private readonly TOKEN_KEY = 'auth_token';
  private readonly USER_KEY = 'current_user';
  // TODO: Temporary flag - remove when implementing proper auth storage
  private isLoggedInFlag = false;
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  public isAuthenticated$ = this.isAuthenticatedSubject.asObservable();

  constructor(private http: HttpClient, private router: Router) {
    this.loadStoredUser();
    this.updateAuthState();
  }

  private updateAuthState(): void {
    const isAuth = this.isLoggedInFlag || this.getToken() !== null;
    this.isAuthenticatedSubject.next(isAuth);
  }

  private loadStoredUser(): void {
    const token = localStorage.getItem(this.TOKEN_KEY);
    const userJson = localStorage.getItem(this.USER_KEY);
    
    if (token && userJson) {
      try {
        const user = JSON.parse(userJson);
        this.currentUserSubject.next(user);
      } catch (e) {
        this.logout();
      }
    }
  }

  login(credentials: LoginRequest): Observable<AuthResponse> {
    // Send credentials as query parameters in the URL
    const params = new HttpParams()
      .set('email', credentials.email)
      .set('password', credentials.password);
    
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, null, { params }).pipe(
      tap(response => {
        console.log('Auth service login response:', response);
        // TODO: Temporary - set login flag for sidebar visibility
        // Remove when implementing proper auth storage
        if (response) {
          this.isLoggedInFlag = true;
          // Set a temporary user for display purposes
          const tempUser: User = {
            id: response.id || 0,
            name: response.name || response.email || 'User',
            email: response.email || '',
            phone: response.phone,
            address: response.address
          };
          this.currentUserSubject.next(tempUser);
          this.updateAuthState(); // Update auth state immediately
        }
        // TODO: Implement auth storage later
        // if (response.token) {
        //   this.storeAuthData(response);
        // }
      })
    );
  }

  register(userData: RegisterRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/register`, userData).pipe(
      tap(response => {
        // TODO: Implement auth storage later
        // if (response.token) {
        //   this.storeAuthData(response);
        // }
      })
    );
  }

  // TODO: Implement auth storage later
  // private storeAuthData(response: AuthResponse): void {
  //   if (response.token) {
  //     localStorage.setItem(this.TOKEN_KEY, response.token);
  //     
  //     const user: User = {
  //       id: response.id,
  //       name: response.name || '',
  //       email: response.email || '',
  //       phone: response.phone,
  //       address: response.address
  //     };
  //     
  //     localStorage.setItem(this.USER_KEY, JSON.stringify(user));
  //     this.currentUserSubject.next(user);
  //   }
  // }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USER_KEY);
    // TODO: Temporary - clear login flag
    this.isLoggedInFlag = false;
    this.currentUserSubject.next(null);
    this.updateAuthState(); // Update auth state immediately
    this.router.navigate(['/login']);
  }

  isAuthenticated(): boolean {
    // TODO: Temporary - check login flag for sidebar visibility
    // Remove when implementing proper auth storage
    if (this.isLoggedInFlag) {
      return true;
    }
    
    const token = this.getToken();
    if (!token) {
      return false;
    }
    
    // Check if token is expired
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      const expiry = payload.exp * 1000;
      return Date.now() < expiry;
    } catch (e) {
      return false;
    }
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }

  // TODO: Temporary method - manually set auth state from response
  // Remove when implementing proper auth storage
  setAuthStateFromResponse(response: any): void {
    if (response) {
      this.isLoggedInFlag = true;
      const tempUser: User = {
        id: response.id || 0,
        name: response.name || response.email || 'User',
        email: response.email || '',
        phone: response.phone,
        address: response.address
      };
      this.currentUserSubject.next(tempUser);
      this.updateAuthState();
    }
  }

  validateToken(): Observable<AuthResponse> {
    return this.http.get<AuthResponse>(`${this.apiUrl}/validate`);
  }
}
