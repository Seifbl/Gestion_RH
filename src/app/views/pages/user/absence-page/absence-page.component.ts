import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Employee } from 'src/app/modals/employee.modal';
import { EmployeeService } from 'src/app/shared/services/employee.service';
import { UserService } from 'src/app/shared/services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-absence-page',
  templateUrl: './absence-page.component.html',
  styleUrls: ['./absence-page.component.css'],
})
export class AbsencePage implements OnInit {
  employeeId: any;
  employee: Employee = new Employee();

  isAuthorized: boolean = false;
  userType: string;
  loggedInUserId: any;

  constructor(
    private route: ActivatedRoute,
    private employeeService: EmployeeService,
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.userService.getUserProperties().subscribe((res1) => {
      this.userType = res1.authorities[0].authority;
      if (this.userType === 'CHEF DEPARTEMENT') {
        this.isAuthorized = true;
      }
      this.userService.getUserInfo(res1.userId).subscribe((res2) => {
        this.loggedInUserId = res2.employeeId;
      });

      this.route.paramMap.subscribe((params) => {
        const stId = +params.get('employeeId')!;
        this.employeeId = stId;
        this.employeeService.getEmployee(this.employeeId).subscribe((res) => {
          this.employee = res;
        });
      });
    });
  }
  markAsAbsent(employeeId: any) {
    this.employeeService.markEmployeeAsAbsent(employeeId).subscribe((res) => {
      if (res === 3) {
        this.router.navigate(['users/list-user']);
      } else if (res === 1) {
        Swal.fire({
          icon: 'error',
          title: 'Impossible De Marquer Cet Employé Comme Absent',
          text: 'Cet Employé Est En Congé',
        });
      } else if (res === 2) {
        Swal.fire({
          icon: 'error',
          title: 'Impossible De Marquer Cet Employé Comme Absent',
          text: 'Cet Employé Est Déja Marquer Comme Absent Pour Aujourdhui ',
        });
      }
    });
  }
  returnToList(){
    this.router.navigate(['users/list-user']);
  }
}
