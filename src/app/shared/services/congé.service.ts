import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { AuthService } from './auth.service';
import { Observable, catchError, throwError } from 'rxjs';
import { DemandeConge } from 'src/app/modals/congé.modal';

@Injectable({
  providedIn: 'root',
})
export class DemandeCongeService {
  private apiUrl = 'http://localhost:3600/demandes-conge';

  constructor(private http: HttpClient, private authService: AuthService) {}

  getAllDemandesConges(): Observable<DemandeConge[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeConge[]>(
        `${this.apiUrl}/getAllDemandesConges`,
        {
          headers,
        }
      );
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }

  getDemandeConge(id: number): Observable<DemandeConge> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http
        .get<DemandeConge>(`${this.apiUrl}/getDemandeCongeById/${id}`, {
          headers,
        })
        .pipe(catchError(this.handleError));
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }

  addDemandeConge(
    demandeConge: DemandeConge,
    employeeId: number
  ): Observable<DemandeConge> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
    if (token) {
      return this.http
        .post<DemandeConge>(
          this.apiUrl + `/addDemandeConge/${employeeId}`,
          demandeConge,
          { headers }
        )
        .pipe(catchError(this.handleError));
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }

  updateDemandeConge(demandeConge: DemandeConge): Observable<DemandeConge> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
    if (token) {
      return this.http
        .put<DemandeConge>(`${this.apiUrl}/editDemandeConge`, demandeConge, {
          headers,
        })
        .pipe(catchError(this.handleError));
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }

  deleteDemandeConge(id: number): Observable<void> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
    if (token) {
      return this.http
        .delete<void>(`${this.apiUrl}/deleteDemandeCongeById/${id}`, {
          headers,
        })
        .pipe(catchError(this.handleError));
    }else {
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

  getAllRecentDemandesConge(): Observable<DemandeConge[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeConge[]>(
        `${this.apiUrl}/getAllRecentDemandesConge`,
        {
          headers,
        }
      );
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }

  checkIfEmployeeHasUntreatedRequest(id: number): Observable<DemandeConge> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http
        .get<DemandeConge>(
          `${this.apiUrl}/checkIfEmployeeHasUntreatedRequest/${id}`,
          {
            headers,
          }
        )
        .pipe(catchError(this.handleError));
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }

  getDemandesCongesByEmployee(employeeId: number): Observable<DemandeConge[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeConge[]>(
        `${this.apiUrl}/findDemandeCongeByEmployeeId/${employeeId}`,
        {
          headers,
        }
      );
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }

  findDemandeCongeByDepartementId(
    departementId: number
  ): Observable<DemandeConge[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeConge[]>(
        `${this.apiUrl}/findDemandeCongeByDepartementId/${departementId}`,
        {
          headers,
        }
      );
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }

  findDemandeCongeForChefDepartement(
    departementId: number
  ): Observable<DemandeConge[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeConge[]>(
        `${this.apiUrl}/findDemandeCongeForChefDepartement/${departementId}`,
        {
          headers,
        }
      );
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }

  findDemandeCongeForAdmin(departementId: number): Observable<DemandeConge[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeConge[]>(
        `${this.apiUrl}/findDemandeCongeForAdmin/${departementId}`,
        {
          headers,
        }
      );
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }
  findAllRecentDemandesCongeForAdmin(): Observable<DemandeConge[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeConge[]>(
        `${this.apiUrl}/findAllRecentDemandesCongeForAdmin`,
        {
          headers,
        }
      );
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }

  findFinalisedDemandeCongeByDepartementId(
    departementId: number
  ): Observable<DemandeConge[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeConge[]>(
        `${this.apiUrl}/findFinalisedDemandeCongeByDepartementId/${departementId}`,
        {
          headers,
        }
      );
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }

  findFinalisedDemandeCongeByEmployeeId(
    employeeId: number
  ): Observable<DemandeConge[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeConge[]>(
        `${this.apiUrl}/findFinalisedDemandeCongeByEmployeeId/${employeeId}`,
        {
          headers,
        }
      );
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }

  findAllFinalisedDemandesConge(

  ): Observable<DemandeConge[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeConge[]>(
        `${this.apiUrl}/findAllFinalisedDemandesConge`,
        {
          headers,
        }
      );
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }
  generatePdf(demandeCongeId: number): Observable<Blob> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    if (token) {
      return this.http.get(`${this.apiUrl}/generateCongePdf/${demandeCongeId}`, {
        headers,
        responseType: 'blob'
      });
    } else {
      throw new Error('Authentication token is missing');
    }
  }

}
