import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Employee } from 'src/app/modals/employee.modal';
import { DemandeFicheDePaie } from 'src/app/modals/fichedepaie.modal';
import { EmployeeService } from 'src/app/shared/services/employee.service';
import { DemandeFicheDePaieService } from 'src/app/shared/services/fichedepaie.service';
import { UserService } from 'src/app/shared/services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-demandefichedep',
  templateUrl: './demandefichedep.component.html',
  styleUrls: ['./demandefichedep.component.scss'],
})
export class DemandefichedepComponent implements OnInit {
  demandeFicheDePaie: DemandeFicheDePaie = new DemandeFicheDePaie();
  userType: string;

  loggedInUserId: any;
  employee: Employee = new Employee();

  constructor(
    private employeeService: EmployeeService,
    private userService: UserService,
    private demandeFicheDePaieService: DemandeFicheDePaieService
  ) {}
  ngOnInit(): void {
    this.userService.getUserProperties().subscribe((res1) => {
      this.userType = res1.authorities[0].authority;

      this.userService.getUserInfo(res1.userId).subscribe((res2) => {
        this.loggedInUserId = res2.employeeId;
      });
    });
  }

  submitRequest() {
    this.employeeService.getEmployee(this.loggedInUserId).subscribe((res) => {
      if (res.ficheDePaieTakenForThisMonth) {
        Swal.fire({
          icon: 'error',
          title: 'Vous avez déjà reçu un bulletin de paie ce mois-ci',
          text: `Réessayez le mois prochain.`,
        });
      } else {
        this.demandeFicheDePaieService
          .checkIfEmployeeHasUntreatedRequest(this.loggedInUserId)
          .subscribe((res) => {
            if (res) {
              Swal.fire({
                icon: 'error',
                title: 'Vous Avez Déjà Une Demande Non Traitée',
                text: `Attendre le traitement de la demande ou supprimer la demande existante et en déposer une nouvelle.`,
              });
            } else {
              this.demandeFicheDePaie.requestDate = new Date();
              if (this.userType === 'CHEF DEPARTEMENT') {
                this.demandeFicheDePaie.approvedByCD = true;
                this.demandeFicheDePaie.status = "enCours"
              }
              this.demandeFicheDePaieService
                .addDemandeFicheDePaie(
                  this.demandeFicheDePaie,
                  this.loggedInUserId
                )
                .subscribe(
                  (response) => {
                    console.log(response);
                    // window.location.reload();
                  },
                  (error) => {
                    console.log(error);
                  }
                );
            }
          });
      }
    });
  }
}
