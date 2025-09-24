import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { Dashboard } from './pages/dashboard/dashboard';
import { KanbanComponent } from './pages/kanban/kanban';

export const routes: Routes = [
    { path: 'login', component: Login },
    { path: 'home', component: Dashboard },
    { path: 'kanban', component: KanbanComponent },
    { path: '**', component: Login }
];
