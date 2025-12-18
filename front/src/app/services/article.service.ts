import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';

export interface Theme {
  id: number;
  name: string;
  description: string;
  createdAt: string;
  updatedAt: string;
}

export interface ArticleCreateDto {
  title: string;
  theme: string;
  content: string;
}

@Injectable({
  providedIn: 'root',
})
export class ArticleService {
  constructor(private http: HttpClient, private authService: AuthService) {}

  getSuscribedArticle(): Observable<any> {
    const headers = this.authService.getAuthHeaders();
    return this.http.get(`${this.authService.apiUrl}api/article/suscribed`, {
      headers,
    });
  }

  getThemes(): Observable<Theme[]> {
    const headers = this.authService.getAuthHeaders();
    return this.http.get<Theme[]>(`${this.authService.apiUrl}api/theme`, {
      headers,
    });
  }

  createArticle(body: ArticleCreateDto): Observable<any> {
    const headers = this.authService.getAuthHeaders();
    return this.http.post(`${this.authService.apiUrl}api/article`, body, {
      headers,
    });
  }
}
