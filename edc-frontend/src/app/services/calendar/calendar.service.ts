import { HttpClient } from '@angular/common/http';
import { formatDate } from '@angular/common';
import { Injectable } from '@angular/core';
import { Observable, Subject, timer, of, merge } from 'rxjs';
import { switchMap, retry, share, takeUntil, catchError, map } from 'rxjs/operators';
import { Day } from '../../model/day'
import { CalendarYear } from '../../model/calendar'

@Injectable({
  providedIn: 'root'
})
export class CalendarService {

  private url = this.determineUrl();
  private state: Observable<CalendarYear | null>;
  private stopPolling = new Subject();
  private updated = new Subject();
  
  constructor(private http: HttpClient) {
    timer(1, 60000)
        .subscribe(val => {
          this.url = this.determineUrl();
          this.updated.next();
        });
    this.state = merge(
          timer(1, 10000),
          this.updated.asObservable())
        .pipe(
          switchMap(() => this.http.get<Day[]>(this.url)),
          map(val => new CalendarYear(val)),
          retry(),
          share(),
          catchError(err => of(null)),
          takeUntil(this.stopPolling));
  }

  getState(): Observable<CalendarYear | null> {
    this.updated.next();
    return this.state;
  }

  ngOnDestroy() {
    this.stopPolling.next();
  }
  
  determineUrl(): string {
    return '/api/users/steven/achievement/'
        + formatDate(new Date(), 'yyyy', 'en-GB');
  }
}
