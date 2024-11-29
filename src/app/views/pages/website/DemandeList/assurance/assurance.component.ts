import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { Route, Router } from '@angular/router';
import { LocalDataSource } from 'ng2-smart-table';
import { DemandeRemboursementAssurance } from 'src/app/modals/assurance.modal';
import { DemandeRemboursementAssuranceService } from 'src/app/shared/services/assurance.service';
import { UserService } from 'src/app/shared/services/user.service';

import Swal from 'sweetalert2';

@Component({
  selector: 'app-assurance',
  templateUrl: './assurance.component.html',
  styleUrls: ['./assurance.component.scss'],
})
export class AssuranceComponent implements OnInit {
  constructor(
    private router: Router,
    private userService: UserService,
    private demandeRemboursementAssuranceService: DemandeRemboursementAssuranceService,
    private sanitizer: DomSanitizer
  ) {}

  source: LocalDataSource = new LocalDataSource();
  listDemandes: DemandeRemboursementAssurance[] = [];
  userType: any;
  employee: any;
  isAuthorized: boolean = false;

  settings = {
    columns: {
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
      employee: {
        title: 'Employee',
        type: 'html',
        valuePrepareFunction: (employee: { name: any; lastName: any }) => {
          return employee ? `${employee.name} ${employee.lastName}` : '';
        },
      },

      documentName: {
        title: 'Document',
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

  ngOnInit(): void {
    this.userService.getUserProperties().subscribe(
      (res1) => {
        this.userType = res1.authorities[0].authority;
        if (this.userType === 'ADMIN') {
          this.isAuthorized = true;
          this.userService.getUserInfo(res1.userId).subscribe((res2) => {
            this.employee = res2;
            console.log(this.userType);
            if (res2) {
              this.fetchDemandes();
            }
          });
        } else if (this.userType === 'CHEF DEPARTEMENT') {
          this.isAuthorized = true;
          this.userService.getUserInfo(res1.userId).subscribe((res2) => {
            this.employee = res2;
            console.log(this.userType);
            if (res2) {
              this.fetchDemandes(res2.departement.departementId);
            }
          });
        }
      },
      (error) => {
        console.error('Error fetching data:', error);
      }
    );
  }

  fetchDemandes(id?: any) {
    if (id) {
      this.demandeRemboursementAssuranceService
        .findDemandeRemboursementAssuranceForChefDepartement(id)
        .subscribe((res) => {
          this.listDemandes = res;
          this.source.load(this.listDemandes);
        });
    } else {
      this.demandeRemboursementAssuranceService
        .findAllRecentDemandesRemboursementAssuranceForAdmin()
        .subscribe((res) => {
          console.log(res);
          this.listDemandes = res;
          this.source.load(this.listDemandes);
        });
    }
  }
  onCustomAction(event: any) {
    switch (event.action) {
      case 'consult':
        this.goToDemandePage(event.data);
        break;

      default:
        break;
    }
  }
  goToDemandePage(demande: any) {
    let demandeType = 'demande-remboursement-assurance';
    this.router.navigate([
      `website/demande-details/${demandeType}`,
      demande.demandeRemboursementAssuranceId,
    ]);
  }
}
