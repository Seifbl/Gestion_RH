import {
    HttpClient,
    HttpErrorResponse,
    HttpHeaders,
  } from '@angular/common/http';
  import { Injectable } from '@angular/core';
  import { AuthService } from './auth.service';
  import { Observable, catchError, throwError } from 'rxjs';
  import { DemandeRemboursementAssurance } from 'src/app/modals/assurance.modal';
  
  @Injectable({
    providedIn: 'root',
  })
  export class DemandeRemboursementAssuranceService {
    private apiUrl = 'http://localhost:3600/demandes-remboursement-assurance';
  
    constructor(private http: HttpClient, private authService: AuthService) {}
  
    getAllDemandesRemboursementAssurance(): Observable<
      DemandeRemboursementAssurance[]
    > {
      let token = this.authService.getToken();
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      if (token) {
        return this.http.get<DemandeRemboursementAssurance[]>(
          `${this.apiUrl}/getAllDemandesRemboursementAssurance`,
          {
            headers,
          }
        );
      }else {
        console.log('Token Not Available');
        return new Observable();
      }
    }
  
    getDemandeRemboursementAssurance(
      id: number
    ): Observable<DemandeRemboursementAssurance> {
      let token = this.authService.getToken();
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      if (token) {
        return this.http
          .get<DemandeRemboursementAssurance>(
            `${this.apiUrl}/getDemandeRemboursementAssuranceById/${id}`,
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
  
     addDemandeRemboursement(
       employeeId: number,
       demandeRemboursementAssurance: DemandeRemboursementAssurance,
       file: File
     ): Observable<DemandeRemboursementAssurance> {
       const formData = new FormData();
       formData.append(
         'demandeRemboursementAssurance',
         new Blob([JSON.stringify(demandeRemboursementAssurance)], {
           type: 'application/json',
         })
       );
  
       formData.append('file', file);
  
       let token = this.authService.getToken();
  
       let headers = new HttpHeaders();
       headers = new HttpHeaders({
         Authorization: `Bearer ${token}`,
       });
  
       if (token) {
         return this.http
           .post<DemandeRemboursementAssurance>(
             `${this.apiUrl}/addDemandeRemboursementAssurance/${employeeId}`,
             formData,
             { headers }
           )
           .pipe(catchError(this.handleError));
       }else {
        console.log('Token Not Available');
        return new Observable();
      }
     }
  
  
    updateDemandeRemboursementAssurance(
      demandeRemboursementAssurance: DemandeRemboursementAssurance
    ): Observable<DemandeRemboursementAssurance> {
      let token = this.authService.getToken();
      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      });
      if (token) {
        return this.http
          .put<DemandeRemboursementAssurance>(
            `${this.apiUrl}/editDemandeRemboursementAssurance`,
            demandeRemboursementAssurance,
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
  
    deleteDemandeRemboursementAssurance(id: number): Observable<void> {
      let token = this.authService.getToken();
      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      });
      if (token) {
        return this.http
          .delete<void>(
            `${this.apiUrl}/deleteDemandeRemboursementAssuranceById/${id}`,
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
      } else if (errorResponse.error) {
        console.error('Server Side Error :', errorResponse.error);
      } else {
        console.error('Unknown Server Side Error');
      }
      return throwError(
        'There is a problem with the service. Please try again later.'
      );
    }
  
    getAllRecentDemandesRemboursementAssurance(): Observable<
      DemandeRemboursementAssurance[]
    > {
      let token = this.authService.getToken();
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      if (token) {
        return this.http.get<DemandeRemboursementAssurance[]>(
          `${this.apiUrl}/getAllRecentDemandesRemboursementAssurance`,
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
    ): Observable<DemandeRemboursementAssurance> {
      let token = this.authService.getToken();
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      if (token) {
        return this.http
          .get<DemandeRemboursementAssurance>(
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
  
    getDemandesRemboursementAssuranceByEmployeeId(
      employeeId: number
    ): Observable<DemandeRemboursementAssurance[]> {
      let token = this.authService.getToken();
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      if (token) {
        return this.http.get<DemandeRemboursementAssurance[]>(
          `${this.apiUrl}/findDemandeRemboursementAssuranceByEmployeeId/${employeeId}`,
          {
            headers,
          }
        );
      }else {
        console.log('Token Not Available');
        return new Observable();
      }
    }
  
    findDemandeRemboursementAssuranceByDepartementId(
      departementId: number
    ): Observable<DemandeRemboursementAssurance[]> {
      let token = this.authService.getToken();
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      if (token) {
        return this.http.get<DemandeRemboursementAssurance[]>(
          `${this.apiUrl}/findDemandeRemboursementAssuranceByDepartementId/${departementId}`,
          {
            headers,
          }
        );
      }else {
        console.log('Token Not Available');
        return new Observable();
      }
    }
  
    findDemandeRemboursementAssuranceForChefDepartement(
      departementId: number
    ): Observable<DemandeRemboursementAssurance[]> {
      let token = this.authService.getToken();
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      if (token) {
        return this.http.get<DemandeRemboursementAssurance[]>(
          `${this.apiUrl}/findDemandeRemboursementAssuranceForChefDepartement/${departementId}`,
          {
            headers,
          }
        );
      }else {
        console.log('Token Not Available');
        return new Observable();
      }
    }
  
    findDemandeRemboursementAssuranceForAdmin(
      departementId: number
    ): Observable<DemandeRemboursementAssurance[]> {
      let token = this.authService.getToken();
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      if (token) {
        return this.http.get<DemandeRemboursementAssurance[]>(
          `${this.apiUrl}/findDemandeRemboursementAssuranceForAdmin/${departementId}`,
          {
            headers,
          }
        );
      }else {
        console.log('Token Not Available');
        return new Observable();
      }
    }
    findAllRecentDemandesRemboursementAssuranceForAdmin(): Observable<
      DemandeRemboursementAssurance[]
    > {
      let token = this.authService.getToken();
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      if (token) {
        return this.http.get<DemandeRemboursementAssurance[]>(
          `${this.apiUrl}/findAllRecentDemandesRemboursementAssuranceForAdmin`,
          {
            headers,
          }
        );
      }else {
        console.log('Token Not Available');
        return new Observable();
      }
    }
  
    findFinalisedDemandeRemboursementAssuranceByDepartementId(
      departementId: number
    ): Observable<DemandeRemboursementAssurance[]> {
      let token = this.authService.getToken();
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      if (token) {
        return this.http.get<DemandeRemboursementAssurance[]>(
          `${this.apiUrl}/findFinalisedDemandeRemboursementAssuranceByDepartementId/${departementId}`,
          {
            headers,
          }
        );
      }else {
        console.log('Token Not Available');
        return new Observable();
      }
    }
    findFinalisedDemandeRemboursementAssuranceByEmployeeId(
      employeeId: number
    ): Observable<DemandeRemboursementAssurance[]> {
      let token = this.authService.getToken();
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      if (token) {
        return this.http.get<DemandeRemboursementAssurance[]>(
          `${this.apiUrl}/findFinalisedDemandeRemboursementAssuranceByEmployeeId/${employeeId}`,
          {
            headers,
          }
        );
      }else {
        console.log('Token Not Available');
        return new Observable();
      }
    }
    findAllFinalisedDemandesRemboursementAssurance(
   ): Observable<DemandeRemboursementAssurance[]> {
      let token = this.authService.getToken();
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      if (token) {
        return this.http.get<DemandeRemboursementAssurance[]>(
          `${this.apiUrl}/findAllFinalisedDemandesRemboursementAssurance`,
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
  


