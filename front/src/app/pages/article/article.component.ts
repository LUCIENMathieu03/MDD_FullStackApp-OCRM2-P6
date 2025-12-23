import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Article, ArticleService } from 'src/app/services/article.service';

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.scss'],
})
export class ArticleComponent implements OnInit {
  commentForm!: FormGroup;
  article: Article | null = null;
  invalidComment: boolean = false;

  constructor(
    private articleService: ArticleService,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    const articleId = this.route.snapshot.paramMap.get('id');
    this.loadArticle(+articleId!);

    this.commentForm = this.fb.group({
      content: ['', [Validators.required, Validators.minLength(1)]],
    });
  }

  loadArticle(articleId: number) {
    this.articleService
      .getArticleDetail(articleId)
      .subscribe((data: Article) => {
        this.article = data;
      });
  }

  backHome(): void {
    this.router.navigate(['/home']);
  }

  onSubmit(): void {
    if (this.commentForm.valid && this.article) {
      const commentaire = this.commentForm.value;
      this.articleService
        .commentArticle(this.article.id, commentaire)
        .subscribe({
          next: (response) => {
            console.log('Login OK:', response);
            this.invalidComment = false;
            this.commentForm.get('content')?.setValue('');
            this.loadArticle(+this.route.snapshot.paramMap.get('id')!);
            window.scrollTo({
              top: document.body.scrollHeight,
              behavior: 'smooth',
            });
          },
          error: (error) => {
            console.error('Login échoué:', error);
            console.log(commentaire);
            this.invalidComment = true;
          },
        });
    } else {
      this.invalidComment = true;
    }
  }
  resetError() {
    this.invalidComment = false;
  }
}
