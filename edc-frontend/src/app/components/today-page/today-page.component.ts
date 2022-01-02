import { Component, OnInit } from '@angular/core';
import { formatDate } from '@angular/common';
import { Day, DayStatus, Achievement } from '../../model/day';
import { DayService } from '../../services/day/day.service';

@Component({
  selector: 'app-today-page',
  templateUrl: './today-page.component.html',
  styleUrls: ['./today-page.component.css']
})
export class TodayPageComponent implements OnInit {

  state: Day | null = null;

  constructor(private dayService: DayService) {
    this.dayService.getState().subscribe(val => this.state = val);
  }

  ngOnInit(): void {
  }
  
  toggle(achievement: Achievement): void {
    achievement.achieved = !achievement.achieved;
    this.dayService.pushState(this.state!);
  }
}
