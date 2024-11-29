import { Component, OnInit } from '@angular/core';
import {
  FormGroup,
  FormControl,
  FormBuilder,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DemandeAvanceSalaire } from 'src/app/modals/avance.modal';
import { Employee } from 'src/app/modals/employee.modal';
import { DemandeAvanceSalaireService } from 'src/app/shared/services/avance.service';
import { EmployeeService } from 'src/app/shared/services/employee.service';
import { UserService } from 'src/app/shared/services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-demandeavance',
  templateUrl: './demandeavance.component.html',
  styleUrls: ['./demandeavance.component.scss'],
})
export class DemandeavanceComponent implements OnInit {
  submitted: boolean = false;
  demandeAvanceSalaire: DemandeAvanceSalaire = new DemandeAvanceSalaire();
  userType: string;

  public DemandeAvanceSalaireForm: FormGroup = new FormGroup({
    montantAvance: new FormControl(''),
  });

  loggedInUserId: any;
  employee: Employee = new Employee();

  constructor(
    private fb: FormBuilder,
    private employeeService: EmployeeService,
    private userService: UserService,
    private demandeAvanceSalaireService: DemandeAvanceSalaireService
  ) {}

  ngOnInit(): void {
    this.DemandeAvanceSalaireForm = this.fb.group({
      montantAvance: ['', Validators.required],
    });

    this.userService.getUserProperties().subscribe((res1) => {
      this.userType = res1.authorities[0].authority;

      this.userService.getUserInfo(res1.userId).subscribe((res2) => {
        this.loggedInUserId = res2.employeeId;
        this.employeeService.getEmployee(res2.employeeId).subscribe((res) => {
          this.employee = res;
        });
      });
    });
  }

  onSubmit() {
    this.employeeService.getEmployee(this.loggedInUserId).subscribe((res) => {
      if (res.avanceSalaireTakenForThisMonth) {
        Swal.fire({
          icon: 'error',
          title:
            'Vous Avez Déjà Demander Une Avance Sur Votre Salaire De Ce Mois.',
          text: `Réessayez le mois prochain.`,
        });
      } else {
        this.demandeAvanceSalaireService
          .checkIfEmployeeHasUntreatedRequest(this.loggedInUserId)
          .subscribe((res) => {
            if (res) {
              Swal.fire({
                icon: 'error',
                title: 'Vous Avez Déjà Une Demande Non Traitée',
                text: `Attendre le traitement de la demande ou supprimer la demande existante et en déposer une nouvelle.`,
              });
            } else {
              this.submitted = true;
              if (this.DemandeAvanceSalaireForm.invalid) {
                return;
              }

              if (this.submitted) {
                if (this.employee.salary < this.demandeAvanceSalaire.montant) {
                  Swal.fire({
                    icon: 'error',
                    title:
                      'Avance Sur Salaire Ne Peut Pas Dépasser Le Salaire Actuel',
                    text: 'Essayez un montant inférieur',
                  });
                } else {
                  this.demandeAvanceSalaire.requestDate = new Date();
                  if (this.userType === 'CHEF DEPARTEMENT') {
                    this.demandeAvanceSalaire.approvedByCD = true;
                    this.demandeAvanceSalaire.status = "enCours"
                  }
                  this.demandeAvanceSalaireService
                    .addDemandeAvanceSalaire(
                      this.demandeAvanceSalaire,
                      this.loggedInUserId
                    )
                    .subscribe(
                      (response) => {
                        console.log(response);
                        window.location.reload();
                      },
                      (error) => {
                        console.log(error);
                      }
                    );
                }
              }
            }
          });
      }
    });
  }
}
