import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject, timer, of, merge } from 'rxjs';
import { switchMap, retry, share, takeUntil, catchError } from 'rxjs/operators';
import { Streak } from '../../model/streak'

@Injectable({
  providedIn: 'root'
})
export class StreakService {

  private url = '/api/users/steven/streak/';
  private state: Observable<Streak | null>;
  private stopPolling = new Subject();
  private updated = new Subject();
  
  constructor(private http: HttpClient) {
    this.state = merge(
          timer(1, 10000),
          this.updated.asObservable())
        .pipe(
          switchMap(() => this.http.get<Streak>(this.url)),
          retry(),
          share(),
          catchError(err => of(null)),
          takeUntil(this.stopPolling));
  }

  getState(): Observable<Streak | null> {
    this.updated.next();
    return this.state;
  }

  ngOnDestroy() {
    this.stopPolling.next();
  }
}
