import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ArticleService, Theme } from 'src/app/services/article.service';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.scss'],
})
export class CreateComponent implements OnInit {
  createForm: FormGroup;
  createError: boolean = false;
  themes: Theme[] = [];

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private articleService: ArticleService
  ) {
    this.createForm = this.fb.group({
      title: ['', [Validators.required]],
      theme: ['', [Validators.required]],
      content: ['', [Validators.required]],
    });
  }

  ngOnInit(): void {
    this.articleService.getThemes().subscribe({
      next: (data) => (this.themes = data),
      error: (err) => console.error('Erreur chargement thèmes', err),
    });
  }

  backHome(): void {
    this.router.navigate(['/']);
  }

  resetCreateError() {
    this.createError = false;
  }

  onSubmit(): void {
    if (this.createForm.invalid) {
      this.createForm.markAllAsTouched();
      return;
    }

    const selectedThemeId = this.createForm.value.theme;
    const selectedTheme = this.themes.find(
      (t) => t.id === parseInt(selectedThemeId)
    );

    const body = {
      title: this.createForm.value.title!,
      theme: selectedTheme ? selectedTheme.name : '',
      content: this.createForm.value.content!,
    };

    console.log('Body envoyé:', body);

    this.articleService.createArticle(body).subscribe({
      next: () => {
        this.createError = false;
        this.router.navigate(['/home']);
      },
      error: (err) => {
        console.error('Erreur création article', err);
        this.createError = true;
      },
    });
  }
}
