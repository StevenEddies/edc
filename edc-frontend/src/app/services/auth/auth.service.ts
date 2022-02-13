import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, BehaviorSubject } from 'rxjs';
import { User } from '../../model/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private user: BehaviorSubject<User | null>;
  
  constructor(private router: Router) {
    var storedJson = localStorage.getItem('user');
    var storedUser = storedJson ? JSON.parse(storedJson) : null;
    this.user = new BehaviorSubject<User | null>(storedUser);
  }
  
  getUser(): Observable<User | null> {
    return this.user.asObservable();
  }
  
  getUserValue(): User | null {
    return this.user.getValue();
  }
  
  login(user: User): void {
    this.user.next(user);
    localStorage.setItem('user', JSON.stringify(user));
    this.router.navigate(['/today']);
  }
  
  logout(): void {
    this.user.next(null);
    localStorage.removeItem('user');
    this.router.navigate(['/login']);
  }
}
