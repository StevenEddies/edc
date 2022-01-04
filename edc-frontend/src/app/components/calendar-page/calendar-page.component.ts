import { Component, OnInit } from '@angular/core';
import { CalendarYear } from '../../model/calendar';
import { DayStatus } from '../../model/day';
import { CalendarService } from '../../services/calendar/calendar.service';

@Component({
  selector: 'app-calendar-page',
  templateUrl: './calendar-page.component.html',
  styleUrls: ['./calendar-page.component.css']
})
export class CalendarPageComponent implements OnInit {

  DayStatus = DayStatus;

  state: CalendarYear | null = null;

  constructor(private calendarService: CalendarService) {
    this.calendarService.getState().subscribe(val => this.state = val);
  }

  ngOnInit(): void {
  }

}
