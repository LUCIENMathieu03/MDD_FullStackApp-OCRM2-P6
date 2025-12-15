import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  isMobileMenuOpen: boolean = false;

  constructor(private router: Router, protected authService: AuthService) {}

  ngOnInit(): void {}

  toggleMenu() {
    this.isMobileMenuOpen = !this.isMobileMenuOpen;
  }

  closeMobileMenu() {
    this.isMobileMenuOpen = false;
  }

  goToHomePage() {
    this.router.navigate(['/home']);
  }

  logout() {
    this.authService.logout();
    this.isMobileMenuOpen = false;
    this.router.navigate(['']);
  }
}
