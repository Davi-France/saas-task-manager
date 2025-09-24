import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TaskInterface } from '../models/task.model';
import { AuthService } from './authService';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  private apiUrl = "http://localhost:8080/tasks";

  constructor(private authService: AuthService, private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken()
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    })
  }

  getTasks(): Observable<TaskInterface[]> {
    return this.http.get<TaskInterface[]>(this.apiUrl, { headers: this.getHeaders() });
  }

  getTask(id: number): Observable<TaskInterface> {
    return this.http.get<TaskInterface>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
  }

  createTask(task: TaskInterface): Observable<TaskInterface> {
    return this.http.post<TaskInterface>(`${this.apiUrl}`, task, { headers: this.getHeaders() });
  }

  updateTask(id: number, task: TaskInterface): Observable<TaskInterface> {
    return this.http.put<TaskInterface>(`${this.apiUrl}/${id}`, task, { headers: this.getHeaders() });
  }

  deleteTask(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
  }
}
