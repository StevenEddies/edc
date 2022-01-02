export interface Day {
  day: Date;
  achievements: Achievement[];
  status: DayStatus;
}

export interface Achievement {
  goal: string;
  achieved: boolean;
}

export enum DayStatus {
  NotDue = "NOT_DUE",
  Incomplete = "INCOMPLETE",
  PartiallyComplete = "PARTIALLY_COMPLETE",
  Complete = "COMPLETE"
}
