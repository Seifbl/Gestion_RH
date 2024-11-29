import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Employee } from 'src/app/modals/employee.modal';
import { EmployeeService } from 'src/app/shared/services/employee.service';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit {
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
      if (this.userType === 'ADMIN') {
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
  updateUser(employeeId: any) {
    this.router.navigate(['users/edit-profile', employeeId]);
  }
  changePassword(employeeId: any) {
    this.router.navigate(['users/change-password', employeeId]);
  }
}
