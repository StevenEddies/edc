import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StreakPageComponent } from './streak-page.component';

describe('StreakPageComponent', () => {
  let component: StreakPageComponent;
  let fixture: ComponentFixture<StreakPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StreakPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StreakPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
