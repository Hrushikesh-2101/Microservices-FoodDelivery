import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService, private router: Router) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const token = this.authService.getToken();

    // Don't add token to login/register endpoints (they don't require authentication)
    // But validate endpoint needs the token
    const isAuthEndpoint = request.url.includes('/api/auth/login') || 
                          request.url.includes('/api/auth/register');
    
    if (!isAuthEndpoint && token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }

    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        // TODO: Auth removed temporarily - don't redirect on 401
        // if (error.status === 401) {
        //   this.authService.logout();
        //   this.router.navigate(['/login']);
        // }
        return throwError(() => error);
      })
    );
  }
}
