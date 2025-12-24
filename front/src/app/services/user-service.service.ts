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

  updateUserProfile(userData: {
    name?: string;
    email?: string;
    password?: string;
  }): Observable<User> {
    const headers = this.authService.getAuthHeaders();
    return this.http.put<User>(
      `${this.authService.apiUrl}api/user/me/update`,
      userData,
      { headers }
    );
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

  unSubscribeToTheme(themeId: number): Observable<void> {
    const headers = this.authService.getAuthHeaders();
    return this.http.delete<void>(
      `${this.authService.apiUrl}api/user/me/subscription/${themeId}`,
      { headers }
    );
  }
}
