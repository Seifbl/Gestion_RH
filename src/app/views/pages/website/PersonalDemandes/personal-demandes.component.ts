import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LocalDataSource } from 'ng2-smart-table';
import { forkJoin } from 'rxjs';
import { DemandeRemboursementAssurance } from 'src/app/modals/assurance.modal';
import { DemandeAttestationTravail } from 'src/app/modals/attestation.modal';
import { DemandeAvanceSalaire } from 'src/app/modals/avance.modal';
import { DemandeConge } from 'src/app/modals/congé.modal';
import { Employee } from 'src/app/modals/employee.modal';
import { DemandeFicheDePaie } from 'src/app/modals/fichedepaie.modal';
import { DemandeRemboursementAssuranceService } from 'src/app/shared/services/assurance.service';
import { DemandeAttestationTravailService } from 'src/app/shared/services/attestation.service';
import { DemandeAvanceSalaireService } from 'src/app/shared/services/avance.service';
import { DemandeCongeService } from 'src/app/shared/services/congé.service';
import { DemandeFicheDePaieService } from 'src/app/shared/services/fichedepaie.service';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-personal-demandes',
  templateUrl: './personal-demandes.component.html',
  styleUrls: ['./personal-demandes.component.css'],
  preserveWhitespaces: true,
})
export class PersonalDemandesComponent implements OnInit {
  source: LocalDataSource = new LocalDataSource();
  employees: any;
  isAuthorized: Boolean;
  userType: String;
  employee: Employee;

  demandesCongesList: DemandeConge[];
  demandesAvanceSalaireList: DemandeAvanceSalaire[];
  demandeAttestationTravailList: DemandeAttestationTravail[];
  demandeRemboursementAssuranceList: DemandeRemboursementAssurance[];
  demandeFicheDePaieList: DemandeFicheDePaie[];
  allDemandes: any[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private demandeCongeService: DemandeCongeService,
    private demandeAvanceSalaireService: DemandeAvanceSalaireService,
    private demandeAttestationTravailService: DemandeAttestationTravailService,
    private demandeRemboursementAssuranceService: DemandeRemboursementAssuranceService,
    private demandeFicheDePaieService: DemandeFicheDePaieService,
    private changeDetectorRef: ChangeDetectorRef
  ) {
    this.getall();
  }

  settings = {
    columns: {
      type: {
        title: 'Type',
      },

      requestDate: {
        title: 'Date',
        type: 'html',
        valuePrepareFunction: (date: string | number | Date) => {
          if (date) {
            const formattedDate = new Date(date);
            const day = formattedDate.getDate();
            const month = formattedDate.getMonth() + 1;
            const year = formattedDate.getFullYear();
            return `${day < 10 ? '0' + day : day}-${
              month < 10 ? '0' + month : month
            }-${year}`;
          } else {
            return '';
          }
        },
      },

      status: {
        title: 'Etat',
      },
    },
    attr: {
      class: 'table table-responsive',
    },
    actions: {
      columnTitle: '',
      custom: [
        {
          name: 'consult',
          title:
            '<button type="button" class="btn btn-primary me-2 mb-2 mb-md-0">Consult</button>',
        },
      ],
      consult: false,
      edit: false,
      delete: false,

      position: 'right',
    },
  };

  onCustomAction(event: any) {
    switch (event.action) {
      case 'consult':
        this.goToDemandePage(event.data);
        break;

      default:
        break;
    }
  }
  ngOnInit(): void {
    this.userService.getUserProperties().subscribe(
      (res1) => {
        this.userType = res1.authorities[0].authority;

        this.userService.getUserInfo(res1.userId).subscribe((res2) => {
          this.employee = res2;
          console.log(this.userType);
          if (res2) {
            this.fetchAllDemandes(res2.employeeId);
          }
        });
      },
      (error) => {
        console.error('Error fetching data:', error);
      }
    );
  }

  goToDemandePage(demande: any) {
    let demandeType = '';
    switch (demande.type) {
      case 'Conge':
        demandeType = 'demande-conge';
        this.router.navigate([
          `website/ma-demande-details/${demandeType}`,
          demande.demandeCongeId,
        ]);

        break;
      case 'Attestation Travail':
        demandeType = 'demande-attestation-travail';
        this.router.navigate([
          `website/ma-demande-details/${demandeType}`,
          demande.demandeAttestationTravailId,
        ]);
        break;
      case 'Avance Salaire':
        demandeType = 'demande-avance-salaire';
        this.router.navigate([
          `website/ma-demande-details/${demandeType}`,
          demande.demandeAvanceSalaireId,
        ]);
        break;
      case 'Fiche De Paie':
        demandeType = 'demande-fiche-de-paie';
        this.router.navigate([
          `website/ma-demande-details/${demandeType}`,
          demande.demandeFicheDePaieId,
        ]);
        break;
      case 'Remboursement Assurance':
        demandeType = 'demande-remboursement-assurance';
        this.router.navigate([
          `website/ma-demande-details/${demandeType}`,
          demande.demandeRemboursementAssuranceId,
        ]);
        break;
    }
  }

  getall() {}

  fetchAllDemandes(employeeId: any) {
    forkJoin([
      this.demandeCongeService.getDemandesCongesByEmployee(employeeId),
      this.demandeAttestationTravailService.getDemandesAttestationTravailByEmployee(
        employeeId
      ),
      this.demandeAvanceSalaireService.getDemandesAvanceSalaireByEmployee(
        employeeId
      ),
      this.demandeRemboursementAssuranceService.getDemandesRemboursementAssuranceByEmployeeId(
        employeeId
      ),
      this.demandeFicheDePaieService.getDemandesFicheDePaieByEmployee(
        employeeId
      ),
    ]).subscribe(
      ([
        demandeCongeRes,
        demandeAttestTravRes,
        demandeAvanceSalaireRes,
        demandeRembAssRes,
        demandeFichePaieRes,
      ]) => {
        this.demandesCongesList = demandeCongeRes;
        this.demandeAttestationTravailList = demandeAttestTravRes;
        this.demandesAvanceSalaireList = demandeAvanceSalaireRes;
        this.demandeRemboursementAssuranceList = demandeRembAssRes;
        this.demandeFicheDePaieList = demandeFichePaieRes;

        this.allDemandes = [
          ...demandeCongeRes,
          ...demandeAvanceSalaireRes,
          ...demandeAttestTravRes,
          ...demandeRembAssRes,
          ...demandeFichePaieRes,
        ];
        this.allDemandes.sort((a, b) => {
          const dateA = new Date(a.requestDate);
          const dateB = new Date(b.requestDate);
          if (dateA > dateB) {
            return -1;
          } else if (dateA < dateB) {
            return 1;
          } else {
            return 0;
          }
        });
        this.addTypeDemande(this.allDemandes);
        this.source.load(this.allDemandes);
        this.changeDetectorRef.detectChanges();
      }
    );
  }

  addTypeDemande(myDemandes: any[]) {
    myDemandes.forEach((demande: any) => {
      if (demande.hasOwnProperty('demandeCongeId')) {
        demande.type = 'Conge';
      } else if (demande.hasOwnProperty('demandeAttestationTravailId')) {
        demande.type = 'Attestation Travail';
      } else if (demande.hasOwnProperty('demandeRemboursementAssuranceId')) {
        demande.type = 'Remboursement Assurance';
      } else if (demande.hasOwnProperty('demandeAvanceSalaireId')) {
        demande.type = 'Avance Salaire';
      } else if (demande.hasOwnProperty('demandeFicheDePaieId')) {
        demande.type = 'Fiche De Paie';
      } else {
        demande.type = 'Unknown Type';
      }
    });
  }
}
