import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { AuthService } from './auth.service';
import { Observable, catchError, throwError } from 'rxjs';
import { DemandeAttestationTravail } from 'src/app/modals/attestation.modal';

@Injectable({
  providedIn: 'root',
})
export class DemandeAttestationTravailService {
  private apiUrl = 'http://localhost:3600/demandes-attestation-travail';

  constructor(private http: HttpClient, private authService: AuthService) {}

  getAllDemandesAttestationTravail(): Observable<DemandeAttestationTravail[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeAttestationTravail[]>(
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

  getDemandeAttestationTravail(
    id: number
  ): Observable<DemandeAttestationTravail> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http
        .get<DemandeAttestationTravail>(
          `${this.apiUrl}/getDemandeAttestationTravailById/${id}`,
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

  addDemandeAttestationTravail(
    demandeAttestationTravail: DemandeAttestationTravail,
    employeeId: number
  ): Observable<DemandeAttestationTravail> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
    if (token) {
      return this.http
        .post<DemandeAttestationTravail>(
          this.apiUrl + `/addDemandeAttestationTravail/${employeeId}`,
          demandeAttestationTravail,
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

  updateDemandeAttestationTravail(
    demandeAttestationTravail: DemandeAttestationTravail
  ): Observable<DemandeAttestationTravail> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
    if (token) {
      return this.http
        .put<DemandeAttestationTravail>(
          `${this.apiUrl}/editDemandeAttestationTravail`,
          demandeAttestationTravail,
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

  deleteDemandeAttestationTravail(id: number): Observable<void> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
    if (token) {
      return this.http
        .delete<void>(
          `${this.apiUrl}/deleteDemandeAttestationTravailById/${id}`,
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

  getAllRecentDemandesAttestationTravail(): Observable<
    DemandeAttestationTravail[]
  > {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeAttestationTravail[]>(
        `${this.apiUrl}/getAllRecentDemandesAttestationTravail`,
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
  ): Observable<DemandeAttestationTravail> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http
        .get<DemandeAttestationTravail>(
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

  getDemandesAttestationTravailByEmployee(
    employeeId: number
  ): Observable<DemandeAttestationTravail[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeAttestationTravail[]>(
        `${this.apiUrl}/findDemandeAttestationTravailByEmployeeId/${employeeId}`,
        {
          headers,
        }
      );
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }

  findDemandeAttestationTravailByDepartementId(
    departementId: number
  ): Observable<DemandeAttestationTravail[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeAttestationTravail[]>(
        `${this.apiUrl}/findDemandeAttestationTravailByDepartementId/${departementId}`,
        {
          headers,
        }
      );
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }

  findDemandeAttestationTravailForChefDepartement(
    departementId: number
  ): Observable<DemandeAttestationTravail[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeAttestationTravail[]>(
        `${this.apiUrl}/findDemandeAttestationTravailForChefDepartement/${departementId}`,
        {
          headers,
        }
      );
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }

  findDemandeAttestationTravailForAdmin(
    departementId: number
  ): Observable<DemandeAttestationTravail[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeAttestationTravail[]>(
        `${this.apiUrl}/findDemandeAttestationTravailForAdmin/${departementId}`,
        {
          headers,
        }
      );
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }

  findAllRecentDemandesAttestationTravailForAdmin(): Observable<
    DemandeAttestationTravail[]
  > {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeAttestationTravail[]>(
        `${this.apiUrl}/findAllRecentDemandesAttestationTravailForAdmin`,
        {
          headers,
        }
      );
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }

  findFinalisedDemandeAttestationTravailByDepartementId(
    departementId: number
  ): Observable<DemandeAttestationTravail[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeAttestationTravail[]>(
        `${this.apiUrl}/findFinalisedDemandeAttestationTravailByDepartementId/${departementId}`,
        {
          headers,
        }
      );
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }

  findFinalisedDemandeAttestationTravailByEmployeeId(
    employeeId: number
  ): Observable<DemandeAttestationTravail[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeAttestationTravail[]>(
        `${this.apiUrl}/findFinalisedDemandeAttestationTravailByEmployeeId/${employeeId}`,
        {
          headers,
        }
      );
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }

  findFinalisedDemandeAttestationTravail(): Observable<
    DemandeAttestationTravail[]
  > {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeAttestationTravail[]>(
        `${this.apiUrl}/findFinalisedDemandeAttestationTravail`,
        {
          headers,
        }
      );
    }else {
        console.log('Token Not Available');
        return new Observable();
      }
  }

  generateReport(demandeAttestationTravailId: number): Observable<Blob> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    if (token) {
      return this.http.get(`${this.apiUrl}/generate-report/${demandeAttestationTravailId}`, {
        headers,
        responseType: 'blob' 
      });
    } else {
      throw new Error('Authentication token is missing');
    }
  }
  generatePdf(demandeAttestationTravailId: number): Observable<Blob> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    if (token) {
      return this.http.get(`${this.apiUrl}/generatePdf/${demandeAttestationTravailId}`, {
        headers,
        responseType: 'blob'
      });
    } else {
      throw new Error('Authentication token is missing');
    }
  }

}





