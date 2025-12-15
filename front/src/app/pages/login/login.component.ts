import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  connexionError: boolean = false;

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(1)]],
    });
  }

  backHome(): void {
    this.router.navigate(['/']);
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const credentials = this.loginForm.value;
      this.authService.login(credentials).subscribe({
        next: (response) => {
          console.log('Login OK:', response);
          this.connexionError = false;
          this.router.navigate(['/home']);
        },
        error: (error) => {
          console.error('Login échoué:', error);
          this.connexionError = true;
        },
      });
    } else {
      this.connexionError = true;
      console.log('pas bon');
    }
  }

  resetConnexionError() {
    this.connexionError = false;
  }
}
