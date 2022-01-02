import { Component, OnInit, Input } from '@angular/core';
import { formatDate } from '@angular/common';
import { Day, DayStatus } from '../../model/day';

@Component({
  selector: 'app-today-summary-card',
  templateUrl: './today-summary-card.component.html',
  styleUrls: ['./today-summary-card.component.css']
})
export class TodaySummaryCardComponent implements OnInit {

  @Input() state: Day | null = null;

  constructor() { }

  ngOnInit(): void {
  }

  icon(): string {
    switch (this.state?.status) {
      case DayStatus.NotDue:
        return "timer-sand-empty";
      case DayStatus.Incomplete:
        return "circle-outline";
      case DayStatus.PartiallyComplete:
        return "circle-slice-5";
      case DayStatus.Complete:
        return "check-circle-outline";
      default:
        return "alert";
    }
  }

  colour(): string {
    switch (this.state?.status) {
      case DayStatus.Complete:
        return "accent";
      case DayStatus.NotDue:
        return "";
      default:
        return "warn";
    }
  }
  
  title(): string {
    switch (this.state?.status) {
      case DayStatus.NotDue:
        return "Future Day";
      case DayStatus.Incomplete:
        return "Not Started";
      case DayStatus.PartiallyComplete:
        return "Partially Complete";
      case DayStatus.Complete:
        return "Complete";
      default:
        return "Unknown";
    }
  }
}
