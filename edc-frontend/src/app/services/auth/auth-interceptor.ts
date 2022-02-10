import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';

import { AuthService } from './auth.service';

@Injectable()
export class BasicAuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Assume all calls are to our API
    const user = this.authService.getUserValue();
    if (user != null) {
      request = request.clone({
        setHeaders: { 
          Authorization: 'Basic ' + btoa(user.username + ':' + user.password)
        }
      });
    }
    return next.handle(request);
  }
}
