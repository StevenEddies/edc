import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject, timer, of, merge } from 'rxjs';
import { switchMap, retry, share, takeUntil, catchError } from 'rxjs/operators';
import { Day } from '../../model/day'

@Injectable({
  providedIn: 'root'
})
export class DayService {

  private url = '/api/users/steven/achievement/2022/01/02';
  private state: Observable<Day | null>;
  private stopPolling = new Subject();
  private updated = new Subject();
  
  constructor(private http: HttpClient) {
    this.state = merge(
          timer(1, 5000),
          this.updated.asObservable())
        .pipe(
          switchMap(() => this.http.get<Day>(this.url)),
          retry(),
          share(),
          catchError(err => of(null)),
          takeUntil(this.stopPolling));
  }

  getState(): Observable<Day | null> {
    return this.state;
  }

  ngOnDestroy() {
    this.stopPolling.next();
  }
  
  pushState(newState: Day): void {
    this.http.put<void>(this.url, newState)
        .subscribe(val => this.updated.next());
  }
}
