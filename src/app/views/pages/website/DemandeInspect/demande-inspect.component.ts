import { Location } from '@angular/common';
import { Component, OnInit, ChangeDetectorRef, Inject } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormControl,
  Validators,
} from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { Employee } from 'src/app/modals/employee.modal';
import { DemandeRemboursementAssuranceService } from 'src/app/shared/services/assurance.service';
import { DemandeAttestationTravailService } from 'src/app/shared/services/attestation.service';
import { DemandeAvanceSalaireService } from 'src/app/shared/services/avance.service';
import { DemandeCongeService } from 'src/app/shared/services/congé.service';
import { EmployeeService } from 'src/app/shared/services/employee.service';
import { DemandeFicheDePaieService } from 'src/app/shared/services/fichedepaie.service';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-demande-inspect',
  templateUrl: './demande-inspect.component.html',
  styleUrls: ['./demande-inspect.component.css'],
})
export class DemandeInspectComponent implements OnInit {
  demande: any;

  demandeType: string;
  demandeId: any;
  demandeHolder: any;

  submitted: boolean = false;
  replacementList: Employee[];
  demandeDocumentsUrl: any;

  userType: string;
  isAuthorized: boolean = false;

  constructor(
    private demandeCongeService: DemandeCongeService,
    private demandeAttestationTravailService: DemandeAttestationTravailService,
    private demandeAvanceSalaireService: DemandeAvanceSalaireService,
    private demandeRemboursementAssuranceService: DemandeRemboursementAssuranceService,
    private demandeFicheDePaieService: DemandeFicheDePaieService,
    private employeeService: EmployeeService,
    private fb: FormBuilder,
    private sanitizer: DomSanitizer,
    private cdr: ChangeDetectorRef,
    private route: ActivatedRoute,
    private userService: UserService,
    private location: Location
  ) {}

  public confirmDemandeForm: FormGroup = new FormGroup({
    confirmDemande: new FormControl(''),
    remplacant: new FormControl(''),
  });

  ngOnInit(): void {
    this.userService.getUserProperties().subscribe(
      (res1) => {
        this.userType = res1.authorities[0].authority;
        if (this.userType === 'ADMIN' || this.userType === 'CHEF DEPARTEMENT') {
          this.isAuthorized = true;
          if (this.isAuthorized) {
            this.route.paramMap.subscribe((params) => {
              const stId = +params.get('demandeId')!;
              this.demandeId = stId;
              const demandeType = params.get('demandeType')!;
              this.demandeType = demandeType;

              if (
                this.demandeType === 'demande-conge' &&
                this.userType === 'CHEF DEPARTEMENT'
              ) {
                this.confirmDemandeForm = this.fb.group({
                  confirmDemande: ['', Validators.required],
                  remplacant: ['', Validators.required],
                });
              } else {
                this.confirmDemandeForm = this.fb.group({
                  confirmDemande: ['', Validators.required],
                });
              }

              if (this.demandeType === 'demande-conge') {
                this.demandeCongeService
                  .getDemandeConge(this.demandeId)
                  .subscribe((res) => {
                    this.demande = res;
                    this.demandeHolder = res.employee;
                    console.log(this.demande);
                    this.employeeService
                      .getListOfWorkingEmployeesInTimeOff(
                        res.employee.departement.departementId,
                        res.employee.employeeId,
                        res.startDate,
                        res.endDate
                      )
                      .subscribe((res) => {
                        this.replacementList = res;
                      });
                  });
              } else if (this.demandeType === 'demande-attestation-travail') {
                this.demandeAttestationTravailService
                  .getDemandeAttestationTravail(this.demandeId)
                  .subscribe((res) => {
                    this.demande = res;
                    this.demandeHolder = res.employee;
                  });
              } else if (this.demandeType === 'demande-avance-salaire') {
                this.demandeAvanceSalaireService
                  .getDemandeAvanceSalaire(this.demandeId)
                  .subscribe((res) => {
                    this.demande = res;
                    this.demandeHolder = res.employee;
                  });
              } else if (
                this.demandeType === 'demande-remboursement-assurance'
              ) {
                this.demandeRemboursementAssuranceService
                  .getDemandeRemboursementAssurance(this.demandeId)
                  .subscribe((res) => {
                    this.demande = res;
                    this.demandeHolder = res.employee;
                    const binaryString = atob(res.requestDocuments);
                    const byteArray = new Uint8Array(binaryString.length);
                    for (let i = 0; i < binaryString.length; i++) {
                      byteArray[i] = binaryString.charCodeAt(i);
                    }
                    const blob = new Blob([byteArray], {
                      type: 'application/pdf',
                    });
                    this.demandeDocumentsUrl =
                      this.sanitizer.bypassSecurityTrustResourceUrl(
                        URL.createObjectURL(blob)
                      );
                  });
              } else if (this.demandeType === 'demande-fiche-de-paie') {
                this.demandeFicheDePaieService
                  .getDemandeFicheDePaie(this.demandeId)
                  .subscribe((res) => {
                    this.demande = res;
                    this.demandeHolder = res.employee;
                  });
              }
            });
          }
        }
      },
      (error) => {
        console.error('Error fetching data:', error);
      }
    );
  }

  get f() {
    return this.confirmDemandeForm.controls;
  }

  onSubmit() {
    this.submitted = true;
    if (this.confirmDemandeForm.invalid) {
      return;
    }
    if (this.submitted) {
      if (this.userType === 'CHEF DEPARTEMENT') {
        this.demande.approvedByCD =
          this.confirmDemandeForm.get('confirmDemande')!.value;

        if (this.demande.approvedByCD) {
          this.demande.status = 'enCours';
        } else {
          this.demande.status = 'deniedByCD';
          this.demande.finalised = true;
        }
      } else if (this.userType === 'ADMIN') {
        this.demande.approvedByAdmin =
          this.confirmDemandeForm.get('confirmDemande')!.value;

        if (this.demande.approvedByAdmin) {
          this.demande.status = 'Accepté';
          this.demande.finalised = true;
        } else {
          this.demande.status = 'deniedByAdmin';
          this.demande.finalised = true;
        }
      }

      if (this.demande.demandeCongeId) {
        if (this.demande.approvedByAdmin) {
          let differenceInDays = this.calculateDifferenceInDays(
            this.demande.startDate,
            this.demande.endDate
          );
          this.demandeHolder.timeOffDays -= differenceInDays;
          this.demandeHolder.nextTimeOffDate = this.demande.startDate;
          this.demandeHolder.takenTimeOffDays += differenceInDays;
          this.demandeHolder.blockedFromDemandeCongeUntil = this.addWeekdays(
            this.demande.startDate,
            5
          );
          this.employeeService
            .updateEmployee(this.demandeHolder)
            .subscribe((res) => {});
        }
        this.demandeCongeService
          .updateDemandeConge(this.demande)
          .subscribe((res) => {
            this.location.back();
          });
      } else if (this.demande.demandeAttestationTravailId) {
        this.demandeAttestationTravailService
          .updateDemandeAttestationTravail(this.demande)
          .subscribe((res) => {
            this.location.back();
          });
      } else if (this.demande.demandeAvanceSalaireId) {
        this.demandeAvanceSalaireService
          .updateDemandeAvanceSalaire(this.demande)
          .subscribe((res) => {
            this.location.back();
          });
      } else if (this.demande.demandeRemboursementAssuranceId) {
        this.demandeRemboursementAssuranceService
          .updateDemandeRemboursementAssurance(this.demande)
          .subscribe((res) => {
            this.location.back();
          });
      } else if (this.demande.demandeFicheDePaieId) {
        this.demandeFicheDePaieService
          .updateDemandeFicheDePaie(this.demande)
          .subscribe((res) => {
            this.location.back();
          });
      }
    }
  }

  calculateDifferenceInDays(startDate: Date, endDate: Date): number {
    let currentDate = new Date(startDate);
    const endDateFormatted = new Date(endDate);

    let differenceInDays = 0;

    while (currentDate <= endDateFormatted) {
      if (currentDate.getDay() !== 6 && currentDate.getDay() !== 0) {
        differenceInDays++;
      }
      currentDate.setDate(currentDate.getDate() + 1);
    }

    return differenceInDays;
  }

  addWeekdays(startDate: any, numWeekdays: any) {
    let weekdaysToAdd = numWeekdays;
    let currentDate = new Date(startDate);

    while (weekdaysToAdd > 0) {
      currentDate.setDate(currentDate.getDate() + 1);
      if (currentDate.getDay() !== 0 && currentDate.getDay() !== 6) {
        weekdaysToAdd--;
      }
    }

    return currentDate;
  }
}
