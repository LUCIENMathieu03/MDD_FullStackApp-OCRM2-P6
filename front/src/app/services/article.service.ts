import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';

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
}
