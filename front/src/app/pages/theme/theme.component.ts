import { Component, OnInit } from '@angular/core';
import { ArticleService, Theme } from 'src/app/services/article.service';
import { UserServiceService } from 'src/app/services/user-service.service';

@Component({
  selector: 'app-theme',
  templateUrl: './theme.component.html',
  styleUrls: ['./theme.component.scss'],
})
export class ThemeComponent implements OnInit {
  themes: Theme[] = [];
  subscriptions: Theme[] = [];

  constructor(
    private articleService: ArticleService,
    private userService: UserServiceService
  ) {}

  ngOnInit(): void {
    this.loadThemeList();

    this.userService.getUserSubscriptions().subscribe({
      next: (data) => {
        this.subscriptions = data;
      },
      error: (err) => console.error('Erreur chargement abonnements', err),
    });
  }

  loadThemeList() {
    this.articleService.getThemes().subscribe({
      next: (data) => (this.themes = data),
      error: (err) => console.error('Erreur chargement thèmes', err),
    });
  }

  isThemeSubscribed(themeId: number): boolean {
    return this.subscriptions.some((sub) => sub.id === themeId);
  }
  subscribeToTheme(themeId: number): void {
    console.log('S abonner au thème:', themeId);
  }
}
