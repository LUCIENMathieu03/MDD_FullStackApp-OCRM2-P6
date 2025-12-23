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

export interface Comment {
  id: number;
  content: string;
  author: string;
  createdAt: string;
  updatedAt: string;
  articleId: number;
}
export interface Article {
  id: number;
  title: string;
  author: string;
  theme: string;
  content: string;
  createdAt: string;
  updatedAt: string;
  comments?: Comment[];
}

@Injectable({
  providedIn: 'root',
})
export class ArticleService {
  constructor(private http: HttpClient, private authService: AuthService) {}

  getArticleDetail(articleId: number): Observable<Article> {
    const headers = this.authService.getAuthHeaders();
    return this.http.get<Article>(
      `${this.authService.apiUrl}api/article/${articleId}`,
      { headers }
    );
  }

  getSuscribedArticle(): Observable<Article[]> {
    const headers = this.authService.getAuthHeaders();
    return this.http.get<Article[]>(
      `${this.authService.apiUrl}api/article/suscribed`,
      {
        headers,
      }
    );
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

  commentArticle(articleId: number, body: { content: string }) {
    const headers = this.authService.getAuthHeaders();
    return this.http.post(
      `${this.authService.apiUrl}api/article/${articleId}/comment`,
      body,
      {
        headers,
      }
    );
  }
}
