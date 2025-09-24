import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { Dashboard } from './pages/dashboard/dashboard';
import { Tasks } from './pages/tasks/tasks';

export const routes: Routes = [
    { path: 'login', component: Login },
    { path: 'home', component: Dashboard },
    { path: 'tasks', component: Tasks },
    { path: '**', component: Login }
];
