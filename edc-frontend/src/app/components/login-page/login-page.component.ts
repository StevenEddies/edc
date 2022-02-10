import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  loginForm = new FormGroup({
      username: new FormControl('', [Validators.required, Validators.pattern('[a-z0-9]+')]),
      password: new FormControl('', [Validators.required, Validators.minLength(8)])
  });

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
  }

  login(): void {
    var user = {
      username: this.loginForm.controls['username'].value,
      password: this.loginForm.controls['password'].value
    };
    this.authService.login(user);
  }
}
