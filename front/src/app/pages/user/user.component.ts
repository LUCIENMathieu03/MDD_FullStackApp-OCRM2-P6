import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ArticleService, Theme } from 'src/app/services/article.service';
import {
  User,
  UserServiceService,
} from 'src/app/services/user-service.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss'],
})
export class UserComponent implements OnInit {
  userForm: FormGroup;
  saveError: boolean = false;
  suscribedThemes: Theme[] = [];

  constructor(
    private fb: FormBuilder,
    private userService: UserServiceService
  ) {
    this.userForm = this.fb.group({
      name: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', []],
    });
  }

  ngOnInit(): void {
    this.loadUser();
    this.loadUserSubscriptions();
  }

  loadUser(): void {
    this.userService.getCurrentUser().subscribe({
      next: (user: User) => {
        this.userForm.patchValue({
          name: user.name,
          email: user.email,
        });
      },
      error: (err) => {
        console.error('Erreur chargement utilisateur', err);
        this.saveError = true;
      },
    });
  }

  loadUserSubscriptions(): void {
    this.userService.getUserSubscriptions().subscribe({
      next: (data) => (this.suscribedThemes = data),
      error: (err) => console.error('Erreur chargement abonnements', err),
    });
  }

  resetSaveError() {
    this.saveError = false;
  }

  onSubmit(): void {
    if (this.userForm.invalid) {
      this.userForm.markAllAsTouched();
      return;
    }
  }
}
