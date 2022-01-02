import { Component, OnInit, Input } from '@angular/core';
import { Achievement } from '../../model/day';

@Component({
  selector: 'app-achievement-card',
  templateUrl: './achievement-card.component.html',
  styleUrls: ['./achievement-card.component.css']
})
export class AchievementCardComponent implements OnInit {

  @Input() achievement: Achievement | null = null;

  constructor() { }

  ngOnInit(): void {
  }

  icon(): string {
    return this.achievement?.achieved
        ? "checkbox-marked-outline"
        : "checkbox-blank-outline";
  }

  colour(): string {
    return this.achievement?.achieved
        ? "accent"
        : "";
  }
}
