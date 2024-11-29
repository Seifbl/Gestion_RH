import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable, catchError, throwError } from 'rxjs';
import { DemandeFicheDePaie } from 'src/app/modals/fichedepaie.modal';

@Injectable({
  providedIn: 'root',
})
export class DemandeFicheDePaieService {
  private apiUrl = 'http://localhost:3600/demandes-fiche-de-paie';

  constructor(private http: HttpClient, private authService: AuthService) {}

  getAllDemandesFicheDePaie(): Observable<DemandeFicheDePaie[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeFicheDePaie[]>(
        `${this.apiUrl}/getAllDemandesFicheDePaie`,
        {
          headers,
        }
      );
    } else {
      console.log('Token Not Available');
      return new Observable();
    }
  }

  getDemandeFicheDePaie(id: number): Observable<DemandeFicheDePaie> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http
        .get<DemandeFicheDePaie>(
          `${this.apiUrl}/getDemandeFicheDePaieById/${id}`,
          {
            headers,
          }
        )
        .pipe(catchError(this.handleError));
    } else {
      console.log('Token Not Available');
      return new Observable();
    }
  }

  addDemandeFicheDePaie(
    demandeFicheDePaie: DemandeFicheDePaie,
    employeeId: number
  ): Observable<DemandeFicheDePaie> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
    if (token) {
      return this.http
        .post<DemandeFicheDePaie>(
          this.apiUrl + `/addDemandeFicheDePaie/${employeeId}`,
          demandeFicheDePaie,
          {
            headers,
          }
        )
        .pipe(catchError(this.handleError));
    } else {
      console.log('Token Not Available');
      return new Observable();
    }
  }

  updateDemandeFicheDePaie(
    demandeFicheDePaie: DemandeFicheDePaie
  ): Observable<DemandeFicheDePaie> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
    if (token) {
      return this.http
        .put<DemandeFicheDePaie>(
          `${this.apiUrl}/editDemandeFicheDePaie`,
          demandeFicheDePaie,
          {
            headers,
          }
        )
        .pipe(catchError(this.handleError));
    } else {
      console.log('Token Not Available');
      return new Observable();
    }
  }

  deleteDemandeFicheDePaie(id: number): Observable<void> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
    if (token) {
      return this.http
        .delete<void>(`${this.apiUrl}/deleteDemandeFicheDePaieById/${id}`, {
          headers,
        })
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

  getAllRecentDemandesFicheDePaie(): Observable<DemandeFicheDePaie[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeFicheDePaie[]>(
        `${this.apiUrl}/getAllRecentDemandesFicheDePaie`,
        {
          headers,
        }
      );
    } else {
      console.log('Token Not Available');
      return new Observable();
    }
  }

  checkIfEmployeeHasUntreatedRequest(
    id: number
  ): Observable<DemandeFicheDePaie> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http
        .get<DemandeFicheDePaie>(
          `${this.apiUrl}/checkIfEmployeeHasUntreatedRequest/${id}`,
          {
            headers,
          }
        )
        .pipe(catchError(this.handleError));
    } else {
      console.log('Token Not Available');
      return new Observable();
    }
  }

  getDemandesFicheDePaieByEmployee(
    employeeId: number
  ): Observable<DemandeFicheDePaie[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeFicheDePaie[]>(
        `${this.apiUrl}/findDemandeFicheDePaieByEmployeeId/${employeeId}`,
        {
          headers,
        }
      );
    } else {
      console.log('Token Not Available');
      return new Observable();
    }
  }

  findDemandeFicheDePaieByDepartementId(
    departementId: number
  ): Observable<DemandeFicheDePaie[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeFicheDePaie[]>(
        `${this.apiUrl}/findDemandeFicheDePaieByDepartementId/${departementId}`,
        {
          headers,
        }
      );
    } else {
      console.log('Token Not Available');
      return new Observable();
    }
  }

  findDemandeFicheDePaieForChefDepartement(
    departementId: number
  ): Observable<DemandeFicheDePaie[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeFicheDePaie[]>(
        `${this.apiUrl}/findDemandeFicheDePaieForChefDepartement/${departementId}`,
        {
          headers,
        }
      );
    } else {
      console.log('Token Not Available');
      return new Observable();
    }
  }

  findDemandeFicheDePaieForAdmin(
    departementId: number
  ): Observable<DemandeFicheDePaie[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeFicheDePaie[]>(
        `${this.apiUrl}/findDemandeFicheDePaieForAdmin/${departementId}`,
        {
          headers,
        }
      );
    } else {
      console.log('Token Not Available');
      return new Observable();
    }
  }
  findAllRecentDemandesFicheDePaieForAdmin(): Observable<DemandeFicheDePaie[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeFicheDePaie[]>(
        `${this.apiUrl}/findAllRecentDemandesFicheDePaieForAdmin`,
        {
          headers,
        }
      );
    } else {
      console.log('Token Not Available');
      return new Observable();
    }
  }

  findFinalisedDemandeFicheDePaieByEmployeeId(
    employeeId: number
  ): Observable<DemandeFicheDePaie[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeFicheDePaie[]>(
        `${this.apiUrl}/findFinalisedDemandeFicheDePaieByEmployeeId/${employeeId}`,
        {
          headers,
        }
      );
    } else {
      console.log('Token Not Available');
      return new Observable();
    }
  }

  findFinalisedDemandeFicheDePaieByDepartementId(
    departementId: number
  ): Observable<DemandeFicheDePaie[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeFicheDePaie[]>(
        `${this.apiUrl}/findFinalisedDemandeFicheDePaieByDepartementId/${departementId}`,
        {
          headers,
        }
      );
    } else {
      console.log('Token Not Available');
      return new Observable();
    }
  }

  findAllFinalisedDemandesFicheDePaie(): Observable<DemandeFicheDePaie[]> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    if (token) {
      return this.http.get<DemandeFicheDePaie[]>(
        `${this.apiUrl}/findAllFinalisedDemandesFicheDePaie`,
        {
          headers,
        }
      );
    } else {
      console.log('Token Not Available');
      return new Observable();
    }
  }
  generatePdf(id: number): Observable<Blob> {
    let token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    if (token) {
      return this.http.get(`${this.apiUrl}/generateFdpPdf/${id}`, {
        headers,
        responseType: 'blob'
      });
    } else {
      throw new Error('Authentication token is missing');
    }
  }
}
