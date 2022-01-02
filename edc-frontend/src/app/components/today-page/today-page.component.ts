import { Component, OnInit } from '@angular/core';
import { formatDate } from '@angular/common';
import { Day, DayStatus, Achievement } from '../../model/day';

@Component({
  selector: 'app-today-page',
  templateUrl: './today-page.component.html',
  styleUrls: ['./today-page.component.css']
})
export class TodayPageComponent implements OnInit {

  today: Date = new Date();
  state: Day = {
    day: new Date(),
    achievements: [
      { goal: "Meditate", achieved: true },
      { goal: "Exercise", achieved: false }
    ],
    status: DayStatus.PartiallyComplete
  };

  constructor() { }

  ngOnInit(): void {
  }
  
  toggle(achievement: Achievement): void {
    achievement.achieved = !achievement.achieved;
  }
}
