import { Component, Input, OnInit } from '@angular/core';

export type CardType = 'article' | 'theme' | 'abonnement';

@Component({
  selector: 'app-card-display',
  templateUrl: './card-display.component.html',
  styleUrls: ['./card-display.component.scss'],
})
export class CardDisplayComponent implements OnInit {
  @Input() title!: string;
  @Input() textContent!: string;
  @Input() cardType!: CardType;

  @Input() date?: string;
  @Input() auteur?: string;
  @Input() alreadySuscribed?: boolean;

  constructor() {}

  ngOnInit(): void {}
}
