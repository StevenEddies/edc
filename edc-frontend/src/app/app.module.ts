import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { MatTabsModule } from '@angular/material/tabs';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatToolbarModule } from '@angular/material/toolbar'; 
import { ReactiveFormsModule } from '@angular/forms';
import { TodayPageComponent } from './components/today-page/today-page.component';
import { NavTabBarComponent } from './components/nav-tab-bar/nav-tab-bar.component';
import { CalendarPageComponent } from './components/calendar-page/calendar-page.component';
import { MatIconRegistry } from '@angular/material/icon';
import { DomSanitizer } from '@angular/platform-browser';
import { TodaySummaryCardComponent } from './components/today-summary-card/today-summary-card.component';
import { AchievementCardComponent } from './components/achievement-card/achievement-card.component';
import { StreakPageComponent } from './components/streak-page/streak-page.component';
import { LoginPageComponent } from './components/login-page/login-page.component';

@NgModule({
  declarations: [
    AppComponent,
    TodayPageComponent,
    NavTabBarComponent,
    CalendarPageComponent,
    TodaySummaryCardComponent,
    AchievementCardComponent,
    StreakPageComponent,
    LoginPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NoopAnimationsModule,
    HttpClientModule,
    MatTabsModule,
    MatCardModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatButtonModule,
    MatInputModule,
    MatFormFieldModule,
    MatToolbarModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(iconRegistry: MatIconRegistry, domSanitizer: DomSanitizer) {
    iconRegistry.addSvgIconSet(
      domSanitizer.bypassSecurityTrustResourceUrl('./assets/mdi.svg')
    );
  }
}
