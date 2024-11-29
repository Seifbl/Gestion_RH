import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:3600/auth';
  isAdmin: boolean = false;

  constructor(private http: HttpClient) {}

  login(credentials: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, credentials);
  }

  //   signup(credentials: any): Observable<any> {
  //     return this.http.post(`${this.apiUrl}/register`, credentials);
  //   }

  logout(): void {
    localStorage.removeItem('token');
  }

  saveToken(token: string): void {
    localStorage.setItem('token', token);

    const expirationTime = 1000 * 60 * 60;

    setTimeout(() => {
      localStorage.removeItem('token');
    }, expirationTime);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isAuthenticated(): boolean {
    const token = this.getToken();
    return !!token;
  }

  addUser(appUser: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/addUser`, appUser);
  }
  changePassword(oldPassword: String, newPassword: String): Observable<any> {
    const url = `${this.apiUrl}/changePassword?oldPassword=${oldPassword}&newPassword=${newPassword}`;
    return this.http.put(url, {});
  }


}





