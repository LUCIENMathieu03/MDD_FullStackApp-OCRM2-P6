import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  isMobileMenuOpen: boolean = false;
  currentRoute = '';

  constructor(private router: Router, protected authService: AuthService) {
    this.getCurrentRoute();
  }

  ngOnInit(): void {}

  toggleMenu() {
    this.isMobileMenuOpen = !this.isMobileMenuOpen;
  }

  closeMobileMenuAndNavigate(route?: string) {
    this.isMobileMenuOpen = false;

    if (route) {
      setTimeout(() => {
        this.router.navigate([route]);
      }, 300);
    }
  }

  goToHomePage() {
    this.router.navigate(['/home']);
  }

  logout() {
    this.authService.logout();
    this.isMobileMenuOpen = false;
    this.router.navigate(['']);
  }

  isRouteActive(route: string): boolean {
    return this.currentRoute === route;
  }

  getCurrentRoute() {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.currentRoute = this.router.url;
      }
    });
  }
}
