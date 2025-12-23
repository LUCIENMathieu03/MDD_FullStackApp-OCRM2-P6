import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  registerError: boolean = false;

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private authService: AuthService
  ) {
    this.registerForm = this.fb.group({
      name: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(1)]], //il faut respecter l'énoncer
    });
  }

  ngOnInit(): void {}

  backHome(): void {
    this.router.navigate(['/']);
  }

  onSubmit(): void {
    if (this.registerForm.valid) {
      const credential = this.registerForm.value;
      this.authService.register(credential).subscribe({
        next: (response) => {
          console.log('Resgister OK:', response);
          this.router.navigate(['login']);
          this.registerError = false;
        },
        error: (error) => {
          console.error('Register failed:', error);
          this.registerError = true;
        },
      });
    } else {
      this.registerError = true;
      console.log('pas bon');
    }
  }

  resetRegisterError() {
    this.registerError = false;
  }
}
