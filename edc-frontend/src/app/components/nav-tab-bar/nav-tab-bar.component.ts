import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-nav-tab-bar',
  templateUrl: './nav-tab-bar.component.html',
  styleUrls: ['./nav-tab-bar.component.css']
})
export class NavTabBarComponent implements OnInit {

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
  }

  logout(): void {
    this.authService.logout();
  }
  
  loggedIn(): boolean {
    return this.authService.getUserValue() != null;
  }
}
