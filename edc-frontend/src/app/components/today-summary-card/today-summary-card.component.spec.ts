import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TodaySummaryCardComponent } from './today-summary-card.component';

describe('TodaySummaryCardComponent', () => {
  let component: TodaySummaryCardComponent;
  let fixture: ComponentFixture<TodaySummaryCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TodaySummaryCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TodaySummaryCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
