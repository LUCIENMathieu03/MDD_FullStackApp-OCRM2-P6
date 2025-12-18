import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ArticleService } from 'src/app/services/article.service';

export type article = {
  id: number;
  title: string;
  author: string;
  theme: string;
  content: string;
  createdAt: string;
  updatedAt: string;
};
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  articles: article[] = [];

  constructor(private articleService: ArticleService, private router: Router) {}

  ngOnInit(): void {
    this.articleService.getSuscribedArticle().subscribe({
      next: (data) => {
        this.articles = data;
      },
      error: (err) => {
        console.error('Erreur lors du chargement des articles', err);
      },
    });
  }

  createArticleClick(): void {
    this.router.navigate(['create']);
  }
}
