import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {TokenStorageService} from "../../../../core/guard/token-storage.service";
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { Departement } from 'src/app/modals/departement.modal';
import { Employee } from 'src/app/modals/employee.modal';
import { DepartementService } from 'src/app/shared/services/department.service';
import { EmployeeService } from 'src/app/shared/services/employee.service';

@Component({
  selector: 'app-adduser',
  templateUrl: './adduser.component.html',
  styleUrls: ['./adduser.component.scss']
})
export class AdduserComponent implements OnInit {
  submitted: boolean = false;
  employee: Employee = new Employee();
  departements: Departement[];

  public addEmployeeForm: FormGroup = new FormGroup({
    cin: new FormControl(''),
    name: new FormControl(''),
    lastName: new FormControl(''),
    function: new FormControl(''),
    departement: new FormControl(''),
    salary: new FormControl(''),
    joinDate: new FormControl(''),
  });

  repeatPassword: string;

  constructor(
    private fb: FormBuilder,
    private employeeService: EmployeeService,
    private router: Router,
    private departementService: DepartementService
  ) {}

  ngOnInit(): void {
    this.addEmployeeForm = this.fb.group({
      cin: ['', Validators.required],
      name: ['', Validators.required],
      lastName: ['', Validators.required],
      function: ['', Validators.required],
      departement: ['', Validators.required],
      salary: ['', Validators.required],
      joinDate: ['', Validators.required],
    });
    this.departementService.getAllDepartements().subscribe((res) => {
      this.departements = res;
    });
  }

  onSubmit() {
    this.submitted = true;
    if (this.addEmployeeForm.invalid) {
      return;
    }

    if (this.submitted) {
      this.employee.onTimeOff = false;
      this.employeeService.addEmployee(this.employee).subscribe(
        (response) => {
          console.log(response);
          this.router.navigate(['users/list-user'])
        },
        (error) => {
          console.log(error);
        }
      );
    }
  }

}
