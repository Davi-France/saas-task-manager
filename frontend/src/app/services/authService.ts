import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = "http://localhost:8080/auth"

  constructor(private http: HttpClient) { }

  login(email: string, password: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, { email, password })
  }

  register(email: string, password: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, { email, password })
  }

  saveToken(token: string) {
    localStorage.setItem("acess_token", token)
  }

  getToken() {
    return localStorage.getItem("acess_token")
  }

  isLoggedIn(): boolean {
    return !!this.getToken()
  }

  logout() {
    localStorage.removeItem("acess_token")
  }
}
