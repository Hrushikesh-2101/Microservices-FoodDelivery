import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface Product {
  id?: number;
  name: string;
  description?: string;
  price: number;
  available: boolean;
  menuId?: number;
  categories?: string[];
}

export interface ProductPage {
  content: Product[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = environment.apiUrl.productService;

  constructor(private http: HttpClient) { }

  getAllProducts(page: number = 0, size: number = 10, sortBy: string = 'id', sortDir: string = 'asc'): Observable<ProductPage> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sortBy', sortBy)
      .set('sortDir', sortDir);
    
    return this.http.get<ProductPage>(this.apiUrl, { params });
  }

  getProductById(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.apiUrl}/${id}`);
  }

  searchProducts(filters: {
    name?: string;
    minPrice?: number;
    maxPrice?: number;
    available?: boolean;
    description?: string;
    menuId?: number;
    categories?: string[];
  }, page: number = 0, size: number = 10): Observable<ProductPage> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    
    if (filters.name) params = params.set('name', filters.name);
    if (filters.minPrice !== undefined) params = params.set('minPrice', filters.minPrice.toString());
    if (filters.maxPrice !== undefined) params = params.set('maxPrice', filters.maxPrice.toString());
    if (filters.available !== undefined) params = params.set('available', filters.available.toString());
    if (filters.description) params = params.set('description', filters.description);
    if (filters.menuId) params = params.set('menuId', filters.menuId.toString());
    if (filters.categories && filters.categories.length > 0) {
      filters.categories.forEach(cat => params = params.append('categories', cat));
    }
    
    return this.http.get<ProductPage>(`${this.apiUrl}/search`, { params });
  }

  filterByPrice(minPrice: number, maxPrice: number, page: number = 0, size: number = 10): Observable<ProductPage> {
    let params = new HttpParams()
      .set('minPrice', minPrice.toString())
      .set('maxPrice', maxPrice.toString())
      .set('page', page.toString())
      .set('size', size.toString());
    
    return this.http.get<ProductPage>(`${this.apiUrl}/filterByPrice`, { params });
  }

  filterByAvailability(available: boolean, page: number = 0, size: number = 10): Observable<ProductPage> {
    let params = new HttpParams()
      .set('available', available.toString())
      .set('page', page.toString())
      .set('size', size.toString());
    
    return this.http.get<ProductPage>(`${this.apiUrl}/filterByAvailability`, { params });
  }
}

