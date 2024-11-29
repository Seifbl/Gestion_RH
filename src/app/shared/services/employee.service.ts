import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
  HttpParams,
} from '@angular/common/http';
import { AuthService } from './auth.service';
import { Observable, catchError, throwError } from 'rxjs';
import { Employee } from 'src/app/modals/employee.modal';

@Injectable({
  providedIn: 'root',
})
export class EmployeeService {
  private apiUrl = 'http://localhost:3600/employees';

  constructor(private http: HttpClient, private authService: AuthService) {}

  getAllEmployees(): Observable<Employee[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<Employee[]>(`${this.apiUrl}/getAllEmployees`, {
        headers,
      });
    } else {
      console.log('Token Not Available');
      return new Observable();
    }
  }

  getEmployee(id: number): Observable<Employee> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http
        .get<Employee>(`${this.apiUrl}/getEmployeeById/${id}`, { headers })
        .pipe(catchError(this.handleError));
    } else {
      console.log('Token Not Available');
      return new Observable();
    }
  }

  addEmployee(employee: Employee): Observable<Employee> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
    if (token) {
      return this.http
        .post<Employee>(this.apiUrl + '/addEmployee', employee, { headers })
        .pipe(catchError(this.handleError));
    } else {
      console.log('Token Not Available');
      return new Observable();
    }
  }

  updateEmployee(employee: Employee): Observable<Employee> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
    if (token) {
      return this.http
        .put<Employee>(`${this.apiUrl}/editEmployee`, employee, { headers })
        .pipe(catchError(this.handleError));
    } else {
      console.log('Token Not Available');
      return new Observable();
    }
  }

  deleteEmployee(id: number): Observable<void> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
    if (token) {
      return this.http
        .delete<void>(`${this.apiUrl}/deleteEmployeeById/${id}`, { headers })
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

  findWorkingEmployeesInDepartment(
    departementId: number,
    employeeId: number,
    startDate: Date,
    endDate: Date
  ): Observable<boolean> {
    let token = this.authService.getToken();
    if (token) {
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      return this.http.get<boolean>(
        `${this.apiUrl}/findWorkingEmployeesInDepartment/${departementId}/${employeeId}/${startDate}/${endDate}`,
        {
          headers,
        }
      );
    } else {
      console.log('Token Not Available');
      return new Observable();
    }
  }

  getListOfWorkingEmployeesInTimeOff(
    departementId: number,
    employeeId: number,
    startDate: Date,
    endDate: Date
  ): Observable<Employee[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<Employee[]>(
        `${this.apiUrl}/findListOfWorkingEmployeesInTimeOff/${departementId}/${employeeId}/${startDate}/${endDate}`,
        {
          headers,
        }
      );
    } else {
      console.log('Token Not Available');
      return new Observable();
    }
  }
  getEmployeesByDepartementId(departementId: number): Observable<Employee[]> {
    let token = this.authService.getToken();
    if (token) {
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      return this.http.get<Employee[]>(
        `${this.apiUrl}/getEmployeesByDepartementId/${departementId}`,
        {
          headers,
        }
      );
    } else {
      console.log('Token Not Available');
      return new Observable();
    }
  }

  markEmployeeAsAbsent(employeeId: number): Observable<number> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
    if (token) {
      return this.http
        .get<number>(`${this.apiUrl}/markEmployeeAsAbsent/${employeeId}`, { headers })
        .pipe(catchError(this.handleError));
    } else {
      console.log('Token Not Available');
      return new Observable();
    }
  }
}
