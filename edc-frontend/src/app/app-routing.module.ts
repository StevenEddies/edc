import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TodayPageComponent } from './components/today-page/today-page.component';
import { CalendarPageComponent } from './components/calendar-page/calendar-page.component';

const routes: Routes = [
  { path: 'today', component: TodayPageComponent },
  { path: 'calendar', component: CalendarPageComponent },
  { path: '', redirectTo: '/today', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }