import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { map, Observable } from 'rxjs';

export interface Theme {
  id: number;
  name: string;
  description: string;
  createdAt: string;
  updatedAt: string;
}

export interface User {
  id: number;
  name: string;
  email: string;
  createdAt: string;
  updatedAt: string;
}

@Injectable({
  providedIn: 'root',
})
export class UserServiceService {
  constructor(private http: HttpClient, private authService: AuthService) {}

  getCurrentUser(): Observable<User> {
    const headers = this.authService.getAuthHeaders();
    return this.http.get<User>(`${this.authService.apiUrl}api/user/me`, {
      headers,
    });
  }

  getUserSubscriptions(): Observable<Theme[]> {
    const headers = this.authService.getAuthHeaders();
    return this.http.get<Theme[]>(
      `${this.authService.apiUrl}api/user/me/subscription`,
      { headers }
    );
  }

  subscribeToTheme(themeId: number): Observable<Theme> {
    const headers = this.authService.getAuthHeaders();
    return this.http.post<Theme>(
      `${this.authService.apiUrl}api/user/me/subscription/${themeId}`,
      {},
      { headers }
    );
  }
}
