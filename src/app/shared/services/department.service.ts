import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { AuthService } from './auth.service';
import { Observable, catchError, throwError } from 'rxjs';
import { Employee } from 'src/app/modals/employee.modal';
import { Departement } from 'src/app/modals/departement.modal';

@Injectable({
  providedIn: 'root',
})
export class DepartementService {
  private apiUrl = 'http://localhost:3600/departements';

  constructor(private http: HttpClient, private authService: AuthService) {}

  getAllDepartements(): Observable<Departement[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<Departement[]>(`${this.apiUrl}/getAllDepartements`, {
        headers,
      });
    } else {
      console.log('Token Not Available');
      return new Observable();
    }
  }

  getDepartement(id: number): Observable<Departement> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http
        .get<Departement>(`${this.apiUrl}/getDepartementById/${id}`, {
          headers,
        })
        .pipe(catchError(this.handleError));
    } else {
      console.log('Token Not Available');
      return new Observable();
    }
  }

  addDepartement(departement: Departement): Observable<Departement> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
    if (token) {
      return this.http
        .post<Departement>(this.apiUrl + '/addDepartement', departement, {
          headers,
        })
        .pipe(catchError(this.handleError));
    } else {
      console.log('Token Not Available');
      return new Observable();
    }
  }

  updateDepartement(departement: Departement): Observable<Departement> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
    if (token) {
      return this.http
        .put<Departement>(`${this.apiUrl}/editDepartement`, departement, {
          headers,
        })
        .pipe(catchError(this.handleError));
    } else {
      console.log('Token Not Available');
      return new Observable();
    }
  }

  deleteDepartement(id: number): Observable<void> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
    if (token) {
      return this.http
        .delete<void>(`${this.apiUrl}/deleteDepartementById/${id}`, { headers })
        .pipe(catchError(this.handleError));
    } else {
      console.log('Token Not Available');
      return new Observable();
    }
  }

  private handleError(errorResponse: HttpErrorResponse) {
    if (errorResponse.error instanceof ErrorEvent) {
      console.error('Client Side Error :', errorResponse.error.message);
    } else {
      console.error('Server Side Error :', errorResponse);
    }
    return throwError(
      'There is a problem with the service. We are notified & working on it. Please try again later.'
    );
  }
}
