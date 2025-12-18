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

@Injectable({
  providedIn: 'root',
})
export class UserServiceService {
  constructor(private http: HttpClient, private authService: AuthService) {}

  getUserSubscriptions(): Observable<Theme[]> {
    const headers = this.authService.getAuthHeaders();
    return this.http.get<Theme[]>(
      `${this.authService.apiUrl}api/user/me/subscription`,
      { headers }
    );
  }

  isUserSubscribedToTheme(themeId: number): Observable<boolean> {
    return this.getUserSubscriptions().pipe(
      map((subscriptions) => subscriptions.some((sub) => sub.id === themeId))
    );
  }
}
