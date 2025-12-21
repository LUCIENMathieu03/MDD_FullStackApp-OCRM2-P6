import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Article, ArticleService } from 'src/app/services/article.service';

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.scss'],
})
export class ArticleComponent implements OnInit {
  article: Article | null = null;

  constructor(
    private articleService: ArticleService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const articleId = this.route.snapshot.paramMap.get('id');
    this.articleService
      .getArticleDetail(+articleId!)
      .subscribe((data: Article) => {
        this.article = data;
        console.log(data);
      });
  }

  backHome(): void {
    this.router.navigate(['/home']);
  }
}
