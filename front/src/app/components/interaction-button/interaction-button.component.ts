import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-interaction-button',
  templateUrl: './interaction-button.component.html',
  styleUrls: ['./interaction-button.component.scss'],
})
export class InteractionButtonComponent implements OnInit {
  @Input() textButton!: string;
  @Input() typeButton!: string;
  @Input() selected!: boolean;

  @Output() clicked = new EventEmitter<void>();

  constructor() {}

  ngOnInit(): void {}

  onClick() {
    this.clicked.emit();
  }
}
