import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError } from 'rxjs';
import { AuthService } from './auth.service';
import { AppUser } from 'src/app/modals/user.modal';
import { Employee } from 'src/app/modals/employee.modal';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private apiUrl = 'http://localhost:3600/users';
  isAdmin: boolean = false;

  constructor(private http: HttpClient, private authService: AuthService) {}

  getUserProperties(): Observable<any> {
    const token = this.authService.getToken();

    if (token) {
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      return this.http.get<any>(`${this.apiUrl}/getUserProperties`, {
        headers,
      });
    } else {
      console.log('Token Not Available');
      return new Observable();
    }
  }

  addUser(appUser: AppUser, selectedRole: any): Observable<any> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
    if (token) {
      return this.http.post<AppUser>(
        `${this.apiUrl}/addUser/${selectedRole}`,
        appUser,
        {
          headers,
        }
      );
    } else {
      console.log('Token Not Available');
      return new Observable();
    }
  }

  changePassword(
    oldPassword: string,
    newPassword: string
  ): Observable<boolean> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
    if (token) {
      const body = { oldPassword, newPassword };
      return this.http.put<boolean>(`${this.apiUrl}/changePassword`, body, {
        headers,
      });
    } else {
      return new Observable<boolean>((observer) => {
        observer.next(false);
        observer.complete();
      });
    }
  }

  getUserInfo(id: number): Observable<Employee> {
    const token = this.authService.getToken();

    if (token) {
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      return this.http.get<Employee>(`${this.apiUrl}/getUserInfo/${id}`, {
        headers,
      });
    } else {
      console.log('Token Not Available');
      return new Observable();
    }
  }
}
