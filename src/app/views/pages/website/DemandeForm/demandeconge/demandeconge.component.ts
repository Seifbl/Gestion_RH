import { Component, OnInit } from '@angular/core';
import {
  FormGroup,
  FormControl,
  FormBuilder,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DemandeConge } from 'src/app/modals/congé.modal';
import { Employee } from 'src/app/modals/employee.modal';
import { DemandeCongeService } from 'src/app/shared/services/congé.service';
import { EmployeeService } from 'src/app/shared/services/employee.service';
import { UserService } from 'src/app/shared/services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-demandeconge',
  templateUrl: './demandeconge.component.html',
  styleUrls: ['./demandeconge.component.scss'],
})
export class DemandecongeComponent implements OnInit {
  submitted: boolean = false;
  demandeConge: DemandeConge = new DemandeConge();
  userType: string;

  loggedInUserId: any;
  employee: Employee = new Employee();

  public demandeCongeForm: FormGroup = new FormGroup({
    typeConge: new FormControl(''),
    startDate: new FormControl(''),
    endDate: new FormControl(''),
  });

  constructor(
    private fb: FormBuilder,
    private employeeService: EmployeeService,
    private userService: UserService,
    private demandeCongeService: DemandeCongeService
  ) {}

  ngOnInit(): void {
    this.demandeCongeForm = this.fb.group({
      typeConge: ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
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
    this.demandeCongeService
      .checkIfEmployeeHasUntreatedRequest(this.loggedInUserId)
      .subscribe((res) => {
        if (res) {
          Swal.fire({
            icon: 'error',
            title: 'Vous Avez Déjà Une Demande Non Traitée',
            text: `Attendre le traitement de la demande ou supprimer la demande existante et en déposer une nouvelle.`,
          });
        } else if (this.employee.blockedFromDemandeCongeUntil) {
          Swal.fire({
            icon: 'error',
            title:
              'Vous êtes actuellement empêché denvoyer dautres demandes de ce type.',
            text: `Essayer une autre fois.`,
          });
        } else {
          this.submitted = true;
          if (this.demandeCongeForm.invalid) {
            return;
          }

          if (this.submitted) {
            let differenceInDays = this.calculateDifferenceInDays(
              this.demandeConge.startDate,
              this.demandeConge.endDate
            );
            const dateCheckResult = this.startDateCheck(
              this.demandeCongeForm.value.startDate,
              this.demandeCongeForm.value.endDate
            );

            if (this.employee.timeOffDays === 0) {
              Swal.fire({
                icon: 'error',
                title: 'Vous Navez Pas De Jours De Congé!',
                text: 'Réessayez Le Mois Prochain',
              });
            } else if (this.employee.timeOffDays < differenceInDays) {
              Swal.fire({
                icon: 'error',
                title: 'Vous Navez Pas Assez De Jours De Congé!',
                text: `Vous avez demandé ${differenceInDays} jours alors que vous en avez ${this.employee.timeOffDays}`,
              });
            } else if (dateCheckResult === 1) {
              Swal.fire({
                icon: 'error',
                title: 'La date doit être après la date actuelle.',
              });
            } else if (dateCheckResult === 2) {
              Swal.fire({
                icon: 'error',
                title:
                  'La date doit être au moins 48 heures après la date actuelle.',
              });
            } else if (dateCheckResult === 3) {
              Swal.fire({
                icon: 'error',
                title:
                  'La date de fin doit être supérieure à la date de début.',
              });
            } else {
              this.employeeService
                .findWorkingEmployeesInDepartment(
                  this.employee.departement.departementId,
                  this.employee.employeeId,
                  this.demandeConge.startDate,
                  this.demandeConge.endDate
                )
                .subscribe((res) => {
                  if (res === false) {
                    Swal.fire({
                      icon: 'error',
                      title:
                        'Il Ny A Personne Pour Vous Couvrir Pendant Votre Absence',
                      text: `Vous pouvez essayer de sélectionner des dates différentes`,
                    });
                  } else {
                    this.demandeConge.requestDate = new Date();
                    this.demandeConge.returnDate = this.setReturnDate(
                      this.demandeConge.endDate
                    );
                    if (this.userType === 'CHEF DEPARTEMENT') {
                      this.demandeConge.approvedByCD = true;
                      this.demandeConge.status = "enCours"
                    }
                    this.demandeCongeService
                      .addDemandeConge(this.demandeConge, this.loggedInUserId)
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
                });
            }
          }
        }
      });
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

  setReturnDate(passedDate: Date) {
    let endDate = new Date(passedDate);
    const returnDate = new Date();

    if (endDate.getDay() === 5) {
      returnDate.setDate(endDate.getDate() + 3);
    } else {
      returnDate.setDate(endDate.getDate() + 1);
    }
    return returnDate;
  }

  startDateCheck(selectedStartDate: Date, selectedEndDate: Date) {
    const passedStartDate = new Date(selectedStartDate);
    const passedEndDate = new Date(selectedEndDate);

    const currentDate = new Date();
    const minDate = new Date(currentDate.getTime() + 48 * 60 * 60 * 1000);

    if (passedStartDate < currentDate) {
      return 1;
      // return 'La date doit être après la date actuelle.';
    } else if (passedStartDate < minDate) {
      return 2;
      // return 'La date doit être au moins 48 heures après la date actuelle.';
    } else if (passedEndDate < passedStartDate) {
      return 3;
    } else {
      return 4;
    }
  }
}
