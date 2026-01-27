export interface User {
  id?: number;
  name: string;
  email: string;
  phone?: string;
  address?: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  name: string;
  email: string;
  password: string;
  phone: string;
  address: string;
}

export interface AuthResponse {
  token?: string;
  id?: number;
  name?: string;
  email?: string;
  phone?: string;
  address?: string;
  message?: string;
}
