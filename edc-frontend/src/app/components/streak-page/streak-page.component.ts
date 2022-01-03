import { Component, OnInit } from '@angular/core';
import { Streak } from '../../model/streak';
import { StreakService } from '../../services/streak/streak.service';

@Component({
  selector: 'app-streak-page',
  templateUrl: './streak-page.component.html',
  styleUrls: ['./streak-page.component.css']
})
export class StreakPageComponent implements OnInit {

  state: Streak | null = null;

  constructor(streakService: StreakService) {
    streakService.getState().subscribe(val => this.state = val);
  }

  ngOnInit(): void {
  }

  currentStreakProgress(): number {
    if (this.state === null) {
      return 0;
    } else {
      return this.state!.currentStreak / this.state!.longestStreak * 100;
    }
  }

  currentStreakColour(): string {
    if (this.state === null) {
      return "warn";
    } else {
      return this.state!.maintained ? "accent" : "warn";
    }
  }

  longestStreakColour(): string {
    if (this.state === null) {
      return "warn";
    } else if (this.state!.longestStreak == this.state!.currentStreak) {
      return this.currentStreakColour();
    } else {
      return "warn";
    }
  }
}
