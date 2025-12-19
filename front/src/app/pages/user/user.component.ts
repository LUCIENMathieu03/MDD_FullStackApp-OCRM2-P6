import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Theme } from 'src/app/services/article.service';
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
  originalValues: any = {};

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
        this.originalValues = { name: user.name, email: user.email };
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

  unSuscribeToTheme(subscriptionId: number) {
    this.userService.unSubscribeToTheme(subscriptionId).subscribe({
      next: (res) => {
        this.loadUserSubscriptions(), console.log('Désabonnement réussi:', res);
      },
      error: (err) => console.error('Erreur:', err),
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

    const updateData: any = {};

    const currentName = this.userForm.get('name')?.value;
    const currentEmail = this.userForm.get('email')?.value;
    const currentPassword = this.userForm.get('password')?.value;

    if (currentName && currentName !== this.originalValues.name) {
      updateData.name = currentName;
    }
    if (currentEmail && currentEmail !== this.originalValues.email) {
      updateData.email = currentEmail;
    }
    if (currentPassword) {
      updateData.password = currentPassword;
    }
    if (Object.keys(updateData).length === 0) {
      console.log('Aucun champ modifié');
      return;
    }

    this.userService.updateUserProfile(updateData).subscribe({
      next: (updatedUser: User) => {
        console.log('Profil mis à jour:', updatedUser);
        this.saveError = false;
        this.loadUser();
        window.location.reload();
        this.userForm.get('password')?.setValue('');
      },
      error: (err) => {
        console.error('Erreur mise à jour:', err);
        this.saveError = true;
      },
    });
  }
}
