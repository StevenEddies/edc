import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TodayPageComponent } from './components/today-page/today-page.component';
import { StreakPageComponent } from './components/streak-page/streak-page.component';
import { CalendarPageComponent } from './components/calendar-page/calendar-page.component';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { AuthGuard } from './services/auth/auth.guard';

const routes: Routes = [
  { path: 'today', component: TodayPageComponent, canActivate: [AuthGuard] },
  { path: 'streak', component: StreakPageComponent, canActivate: [AuthGuard] },
  { path: 'calendar', component: CalendarPageComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginPageComponent },
  { path: '', redirectTo: '/today', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
