import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { MatTabsModule } from '@angular/material/tabs';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { TodayPageComponent } from './components/today-page/today-page.component';
import { NavTabBarComponent } from './components/nav-tab-bar/nav-tab-bar.component';
import { CalendarPageComponent } from './components/calendar-page/calendar-page.component';
import { MatIconRegistry } from '@angular/material/icon';
import { DomSanitizer } from '@angular/platform-browser';
import { TodaySummaryCardComponent } from './components/today-summary-card/today-summary-card.component';
import { AchievementCardComponent } from './components/achievement-card/achievement-card.component';

@NgModule({
  declarations: [
    AppComponent,
    TodayPageComponent,
    NavTabBarComponent,
    CalendarPageComponent,
    TodaySummaryCardComponent,
    AchievementCardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NoopAnimationsModule,
    HttpClientModule,
    MatTabsModule,
    MatCardModule,
    MatIconModule
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
