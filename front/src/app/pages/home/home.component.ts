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
  isAscending: boolean = false;

  constructor(private articleService: ArticleService, private router: Router) {}

  ngOnInit(): void {
    this.loadSuscribedArticle();
  }

  loadSuscribedArticle() {
    this.articleService.getSuscribedArticle().subscribe({
      next: (data) => {
        console.log(data);
        this.articles = data;
        this.toggleSort();
      },
      error: (err) => {
        console.error('Erreur lors du chargement des articles', err);
      },
    });
  }

  toggleSort(): void {
    this.articles.sort((a, b) => {
      const dateA = new Date(a.createdAt).getTime();
      const dateB = new Date(b.createdAt).getTime();

      return this.isAscending ? dateA - dateB : dateB - dateA;
    });

    this.isAscending = !this.isAscending;
  }

  createArticleClick(): void {
    this.router.navigate(['create']);
  }
}
