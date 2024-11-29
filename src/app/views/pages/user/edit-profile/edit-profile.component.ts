import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Departement } from 'src/app/modals/departement.modal';
import { Employee } from 'src/app/modals/employee.modal';
import { DepartementService } from 'src/app/shared/services/department.service';
import { EmployeeService } from 'src/app/shared/services/employee.service';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.scss'],
})
export class EditProfileComponent implements OnInit {
  employeeId: any;
  employee: Employee = new Employee();
  departements: Departement[];

  constructor(
    private route: ActivatedRoute,
    private employeeService: EmployeeService,
    private router: Router,
    private departementService: DepartementService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      const stId = +params.get('employeeId')!;
      this.employeeId = stId;
    });
    this.employeeService.getEmployee(this.employeeId).subscribe((res) => {
      this.employee = res;
    });
    this.departementService.getAllDepartements().subscribe((res) => {
      this.departements = res;
    });
  }

  saveUser() {
    this.employeeService.updateEmployee(this.employee).subscribe(
      (response) => {
        console.log(response);
        this.router.navigate(['users/profil', this.employeeId]);
      },
      (error) => {
        console.log(error);
      }
    );
  }
  assingUserAccount(){
    this.router.navigate(['users/affect-account', this.employeeId]);
  }
}
