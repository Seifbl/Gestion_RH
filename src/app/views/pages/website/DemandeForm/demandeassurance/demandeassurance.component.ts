import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Employee } from 'src/app/modals/employee.modal';

import { EmployeeService } from 'src/app/shared/services/employee.service';
import { DemandeFicheDePaieService } from 'src/app/shared/services/fichedepaie.service';
import { UserService } from 'src/app/shared/services/user.service';
import { DemandeRemboursementAssurance } from '../../../../../modals/assurance.modal';
import {
  FormGroup,
  FormControl,
  FormBuilder,
  Validators,
} from '@angular/forms';
import { DemandeRemboursementAssuranceService } from 'src/app/shared/services/assurance.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-demandeassurance',
  templateUrl: './demandeassurance.component.html',
  styleUrls: ['./demandeassurance.component.scss'],
})
export class DemandeassuranceComponent implements OnInit {
  submitted: boolean = false;
  demandeRemboursementAssurance: DemandeRemboursementAssurance =
    new DemandeRemboursementAssurance();

  public demandeRemboursementAssuranceForm: FormGroup = new FormGroup({
    requestDocuments: new FormControl(''),
  });
  requestFile: File;
  userType: string;

  loggedInUserId: any;
  employee: Employee = new Employee();

  constructor(
    private fb: FormBuilder,
    private employeeService: EmployeeService,
    private userService: UserService,
    private demandeRemboursementAssuranceService: DemandeRemboursementAssuranceService
  ) {}
  ngOnInit(): void {
    this.demandeRemboursementAssuranceForm = this.fb.group({
      requestDocuments: ['', Validators.required],
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
  onFileSelected(event: any): void {
    if (event.target.files.length > 0) {
      this.requestFile = event.target.files[0];
    }
  }
  onSubmit() {
    this.demandeRemboursementAssuranceService
      .checkIfEmployeeHasUntreatedRequest(this.loggedInUserId)
      .subscribe((res) => {
        if (res) {
          Swal.fire({
            icon: 'error',
            title: 'Vous Avez Déjà Une Demande Non Traitée',
            text: `Attendre le traitement de la demande ou supprimer la demande existante et en déposer une nouvelle.`,
          });
        } else {
          this.demandeRemboursementAssurance.employee = this.employee;
          this.submitted = true;
          if (this.demandeRemboursementAssuranceForm.invalid) {
            return;
          }

          if (this.submitted) {
            this.demandeRemboursementAssurance.requestDate = new Date();
            this.demandeRemboursementAssurance.documentName =
              this.requestFile.name;
            if (this.userType === 'CHEF DEPARTEMENT') {
              this.demandeRemboursementAssurance.approvedByCD = true;
              this.demandeRemboursementAssurance.status = "enCours"
            }
            this.requestFile =
              this.demandeRemboursementAssuranceForm.get(
                'requestDocuments'
              )!.value;
            this.demandeRemboursementAssuranceService
              .addDemandeRemboursement(
                this.loggedInUserId,
                this.demandeRemboursementAssurance,
                this.requestFile
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
      });
  }
}
