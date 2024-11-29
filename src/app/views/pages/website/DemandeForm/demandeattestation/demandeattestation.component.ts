import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import {ActivatedRoute, Router} from "@angular/router";
import { DemandeAttestationTravail } from 'src/app/modals/attestation.modal';
import { Employee } from 'src/app/modals/employee.modal';
import { DemandeAttestationTravailService } from 'src/app/shared/services/attestation.service';
import { EmployeeService } from 'src/app/shared/services/employee.service';
import { UserService } from 'src/app/shared/services/user.service';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-demandeattestation',
  templateUrl: './demandeattestation.component.html',
  styleUrls: ['./demandeattestation.component.scss']
})
export class DemandeattestationComponent implements OnInit {

  submitted: boolean = false;
  demandeAttestationTravail: DemandeAttestationTravail =
  new DemandeAttestationTravail();
  userType: string;

  loggedInUserId: any;
  employee: Employee = new Employee();

  public demandeAttestationTravailForm: FormGroup = new FormGroup({
    demandeReason: new FormControl(''),
  });

  constructor(
    private fb: FormBuilder,
    private employeeService: EmployeeService,
    private userService: UserService,
    private demandeAttestationTravailService: DemandeAttestationTravailService
  ) {}


  ngOnInit(): void {
    this.demandeAttestationTravailForm = this.fb.group({
      demandeReason: ['', Validators.required],
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

    this.demandeAttestationTravailService
    .checkIfEmployeeHasUntreatedRequest(this.loggedInUserId)
    .subscribe((res) => {
      if (res) {
        Swal.fire({
          icon: 'error',
          title: 'Vous Avez Déjà Une Demande Non Traitée',
          text: `Attendre le traitement de la demande ou supprimer la demande existante et en déposer une nouvelle.`,
        });
      } else {
        this.demandeAttestationTravail.employee = this.employee;
        this.submitted = true;
        if (this.demandeAttestationTravailForm.invalid) {
          return;
        }
    
        if (this.submitted) {
          this.demandeAttestationTravail.requestDate = new Date();
          if(this.userType ==="CHEF DEPARTEMENT") {
            this.demandeAttestationTravail.approvedByCD = true;
            this.demandeAttestationTravail.status = "enCours"
          }
          this.demandeAttestationTravailService
            .addDemandeAttestationTravail(
              this.demandeAttestationTravail,
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
    });

  }

}
