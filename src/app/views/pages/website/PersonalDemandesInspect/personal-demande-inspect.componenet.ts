import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { Employee } from 'src/app/modals/employee.modal';
import { DemandeRemboursementAssuranceService } from 'src/app/shared/services/assurance.service';
import { DemandeAttestationTravailService } from 'src/app/shared/services/attestation.service';
import { DemandeAvanceSalaireService } from 'src/app/shared/services/avance.service';
import { DemandeCongeService } from 'src/app/shared/services/congé.service';
import { EmployeeService } from 'src/app/shared/services/employee.service';
import { DemandeFicheDePaieService } from 'src/app/shared/services/fichedepaie.service';
import { UserService } from 'src/app/shared/services/user.service';
import Swal from 'sweetalert2';
import { DemandeRemboursementAssurance } from '../../../../modals/assurance.modal';

@Component({
  selector: 'app-personal-demande-inspect',
  templateUrl: './personal-demande-check.component.html',
  styleUrls: ['./personal-demande-inspect.component.css'],
})
export class PersonalDemandeInspectComponent implements OnInit {
  demande: any;

  demandeType: string;
  demandeId: any;

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
    private router: Router
  ) {}

  ngOnInit(): void {
    this.userService.getUserProperties().subscribe(
      (res1) => {
        this.userType = res1.authorities[0].authority;
        if (this.userType === 'USER' || this.userType === 'CHEF DEPARTEMENT') {
          this.isAuthorized = true;
          if (this.isAuthorized) {
            this.route.paramMap.subscribe((params) => {
              const stId = +params.get('demandeId')!;
              this.demandeId = stId;
              const demandeType = params.get('demandeType')!;
              this.demandeType = demandeType;

              if (this.demandeType === 'demande-conge') {
                this.demandeCongeService
                  .getDemandeConge(this.demandeId)
                  .subscribe((res1) => {
                    this.demande = res1;
                    console.log(this.demande);
                  });
              } else if (this.demandeType === 'demande-attestation-travail') {
                this.demandeAttestationTravailService
                  .getDemandeAttestationTravail(this.demandeId)
                  .subscribe((res) => {
                    this.demande = res;
                  });
              } else if (this.demandeType === 'demande-avance-salaire') {
                this.demandeAvanceSalaireService
                  .getDemandeAvanceSalaire(this.demandeId)
                  .subscribe((res) => {
                    this.demande = res;
                  });
              } else if (
                this.demandeType === 'demande-remboursement-assurance'
              ) {
                this.demandeRemboursementAssuranceService
                  .getDemandeRemboursementAssurance(this.demandeId)
                  .subscribe((res) => {
                    this.demande = res;
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

  onDelete() {
    Swal.fire({
      title: `Êtes-vous sûr de vouloir supprimer cette demande ?`,
      text: 'Vous ne pourrez pas revenir en arrière!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Oui!',
    }).then((result) => {
      if (result.isConfirmed) {
        if (
          (this.demande.approvedByAdmin === true ||
            this.demande.approvedByCD === true ||
            this.demande.status !== 'enAttente') &&
          this.demande.status !== 'Accepté'
        ) {
          Swal.fire({
            icon: 'error',
            title: 'Impossible de supprimer la demande',
            text: 'Votre demande est déjà en cours de traitement!',
          });
        } else if (this.demande.status === 'Accepté') {
          Swal.fire({
            icon: 'error',
            title: 'Impossible de supprimer la demande',
            text: 'Votre demande est déjà accepté!',
          });
        } else {
          if (this.demande.demandeCongeId) {
            this.demandeCongeService
              .deleteDemandeConge(this.demande.demandeCongeId)
              .subscribe((res) => {
                Swal.fire({
                  title: 'Supprimé!',
                  text: 'Demande a été supprimée.',
                  icon: 'success',
                });
                this.router.navigate(['website/mes-demandes']);
              });
          } else if (this.demande.demandeAttestationTravailId) {
            this.demandeAttestationTravailService
              .deleteDemandeAttestationTravail(
                this.demande.demandeAttestationTravailId
              )
              .subscribe((res) => {
                Swal.fire({
                  title: 'Supprimé!',
                  text: 'Demande a été supprimée.',
                  icon: 'success',
                });
                this.router.navigate(['website/mes-demandes']);
              });
          } else if (this.demande.demandeAvanceSalaireId) {
            this.demandeAvanceSalaireService
              .deleteDemandeAvanceSalaire(this.demande.demandeAvanceSalaireId)
              .subscribe((res) => {
                Swal.fire({
                  title: 'Supprimé!',
                  text: 'Demande a été supprimée.',
                  icon: 'success',
                });
                this.router.navigate(['website/mes-demandes']);
              });
          } else if (this.demande.demandeRemboursementAssuranceId) {
            this.demandeRemboursementAssuranceService
              .deleteDemandeRemboursementAssurance(
                this.demande.demandeRemboursementAssuranceId
              )
              .subscribe((res) => {
                Swal.fire({
                  title: 'Supprimé!',
                  text: 'Demande a été supprimée.',
                  icon: 'success',
                });
                this.router.navigate(['website/mes-demandes']);
              });
          } else if (this.demande.demandeFicheDePaieId) {
            this.demandeFicheDePaieService
              .deleteDemandeFicheDePaie(this.demande.demandeFicheDePaieId)
              .subscribe((res) => {
                Swal.fire({
                  title: 'Supprimé!',
                  text: 'Demande a été supprimée.',
                  icon: 'success',
                });
                this.router.navigate(['website/mes-demandes']);
              });
          }
        }
      }
    });
  }

  getDemandePdf(demandeType: any, demande: any) {
    if (demandeType === 'demande-conge') {
      this.demandeCongeService
        .generatePdf(demande.demandeCongeId)
        .subscribe((res) => {
          const fileURL = URL.createObjectURL(res);
          window.open(fileURL, '_blank');
        });
    } else if (demandeType === 'demande-fiche-de-paie') {
      this.demandeFicheDePaieService
        .generatePdf(demande.demandeFicheDePaieId)
        .subscribe((res) => {
          const fileURL = URL.createObjectURL(res);
          window.open(fileURL, '_blank');
        });
    } else if (demandeType === 'demande-attestation-travail') {
      this.demandeAttestationTravailService
        .generatePdf(demande.demandeAttestationTravailId)
        .subscribe((res) => {
          const fileURL = URL.createObjectURL(res);
          window.open(fileURL, '_blank');
        });
    }
  }

  onReturn() {
    this.router.navigate(['website/mes-demandes']);
  }
}
