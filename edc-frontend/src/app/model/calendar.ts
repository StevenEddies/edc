import { Day, DayStatus } from './day';
import { formatDate } from '@angular/common';

export class CalendarYear {
  private months: Map<number, CalendarMonth>;
  
  constructor(inputDays: Day[]) {
    this.months = new Map<number, CalendarMonth>();
    for (var eachDay in inputDays) {
      this.addDay(inputDays[eachDay]);
    }
  }
  
  private addDay(inputDay: Day): void {
    let monthNum = parseInt(formatDate(inputDay.day, 'M', 'en-GB'));
    let dayNum = parseInt(formatDate(inputDay.day, 'd', 'en-GB'));
    let dayData = new CalendarDay(dayNum, inputDay);
    this.getMonth(monthNum).addDay(dayNum, dayData);
  }
  
  getMonth(num: number): CalendarMonth {
    if (!this.months.has(num)) {
      this.months.set(num, new CalendarMonth(num));
    }
    return this.months.get(num)!;
  }
  
  allMonths(): IterableIterator<CalendarMonth> {
    return this.months.values();
  }
}

export class CalendarMonth {
  readonly name: string;
  private days: Map<number, CalendarDay> = new Map<number, CalendarDay>();
  private lastDay: number = 0;
  
  constructor(num: number) {
    this.name = formatDate(new Date(2000, num - 1), 'MMM', 'en-GB');
  }
  
  addDay(num: number, day: CalendarDay): void {
    this.days.set(num, day);
    if (num > this.lastDay) {
      this.lastDay = num;
    }
  }
  
  getLastDay(): number {
    return this.lastDay;
  }
  
  getDay(num: number): CalendarDay | undefined {
    return this.days.get(num);
  }
  
  allDays(): IterableIterator<CalendarDay> {
    return this.days.values();
  }
}

export class CalendarDay {
  readonly num: number;
  readonly status: DayStatus;
  readonly today: boolean;
  
  constructor(num: number, day: Day) {
    this.num = num;
    this.status = day.status;
    this.today = (formatDate(new Date(), 'yyyy-MM-dd', 'en-GB') == formatDate(day.day, 'yyyy-MM-dd', 'en-GB'));
  }
}
