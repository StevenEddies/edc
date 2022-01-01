import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavTabBarComponent } from './nav-tab-bar.component';

describe('NavTabBarComponent', () => {
  let component: NavTabBarComponent;
  let fixture: ComponentFixture<NavTabBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NavTabBarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NavTabBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
