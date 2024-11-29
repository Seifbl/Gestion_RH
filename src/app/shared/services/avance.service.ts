import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { AuthService } from './auth.service';
import { Observable, catchError, throwError } from 'rxjs';
import { DemandeAttestationTravail } from 'src/app/modals/attestation.modal';
import { DemandeAvanceSalaire } from 'src/app/modals/avance.modal';

@Injectable({
  providedIn: 'root',
})
export class DemandeAvanceSalaireService {
  private apiUrl = 'http://localhost:3600/demandes-avance-salaire';

  constructor(private http: HttpClient, private authService: AuthService) {}

  getAllDemandesAvanceSalaire(): Observable<DemandeAvanceSalaire[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeAvanceSalaire[]>(
        `${this.apiUrl}/getAllDemandesAvanceSalaire`,
        {
          headers,
        }
      );
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }

  getDemandeAvanceSalaire(id: number): Observable<DemandeAvanceSalaire> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http
        .get<DemandeAvanceSalaire>(
          `${this.apiUrl}/getDemandeAvanceSalaireById/${id}`,
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

  addDemandeAvanceSalaire(
    demandeAvanceSalaire: DemandeAvanceSalaire,
    employeeId: number
  ): Observable<DemandeAvanceSalaire> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
    if (token) {
      return this.http
        .post<DemandeAvanceSalaire>(
          this.apiUrl + `/addDemandeAvanceSalaire/${employeeId}`,
          demandeAvanceSalaire,
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

  updateDemandeAvanceSalaire(
    demandeAvanceSalaire: DemandeAvanceSalaire
  ): Observable<DemandeAvanceSalaire> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
    if (token) {
      return this.http
        .put<DemandeAvanceSalaire>(
          `${this.apiUrl}/editDemandeAvanceSalaire`,
          demandeAvanceSalaire,
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

  deleteDemandeAvanceSalaire(id: number): Observable<void> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
    if (token) {
      return this.http
        .delete<void>(`${this.apiUrl}/deleteDemandeAvanceSalaireById/${id}`, {
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

  getAllRecentDemandesAvanceSalaire(): Observable<DemandeAvanceSalaire[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeAvanceSalaire[]>(
        `${this.apiUrl}/getAllRecentDemandesAvanceSalaire`,
        {
          headers,
        }
      );
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }

  checkIfEmployeeHasUntreatedRequest(
    id: number
  ): Observable<DemandeAvanceSalaire> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http
        .get<DemandeAvanceSalaire>(
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

  getDemandesAvanceSalaireByEmployee(employeeId : number): Observable<DemandeAvanceSalaire[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeAvanceSalaire[]>(
        `${this.apiUrl}/findDemandeAvanceSalaireByEmployeeId/${employeeId}`,
        {
          headers,
        }
      );
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }

  findDemandeAvanceSalaireByDepartementId(departementId : number): Observable<DemandeAvanceSalaire[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeAvanceSalaire[]>(
        `${this.apiUrl}/findDemandeAvanceSalaireByDepartementId/${departementId}`,
        {
          headers,
        }
      );
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }

  findDemandeAvanceSalaireForChefDepartement(departementId : number): Observable<DemandeAvanceSalaire[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeAvanceSalaire[]>(
        `${this.apiUrl}/findDemandeAvanceSalaireForChefDepartement/${departementId}`,
        {
          headers,
        }
      );
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }

  findDemandeAvanceSalaireForAdmin(departementId : number): Observable<DemandeAvanceSalaire[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeAvanceSalaire[]>(
        `${this.apiUrl}/findDemandeAvanceSalaireForAdmin/${departementId}`,
        {
          headers,
        }
      );
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }
  findAllRecentDemandesAvanceSalaireForAdmin(): Observable<DemandeAvanceSalaire[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeAvanceSalaire[]>(
        `${this.apiUrl}/findAllRecentDemandesAvanceSalaireForAdmin`,
        {
          headers,
        }
      );
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }

  findFinalisedDemandeAvanceSalaireByDepartementId(departementId : number): Observable<DemandeAvanceSalaire[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeAvanceSalaire[]>(
        `${this.apiUrl}/findFinalisedDemandeAvanceSalaireByDepartementId/${departementId}`,
        {
          headers,
        }
      );
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }
  findFinalisedDemandeAvanceSalaireByEmployeeId(employeeId : number): Observable<DemandeAvanceSalaire[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeAvanceSalaire[]>(
        `${this.apiUrl}/findFinalisedDemandeAvanceSalaireByEmployeeId/${employeeId}`,
        {
          headers,
        }
      );
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }
  findFinalisedDemandesAvanceSalaire(): Observable<DemandeAvanceSalaire[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeAvanceSalaire[]>(
        `${this.apiUrl}/findFinalisedDemandesAvanceSalaire`,
        {
          headers,
        }
      );
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }
}




