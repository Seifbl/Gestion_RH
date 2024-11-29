import { Component, OnInit } from '@angular/core';
import {
  FormGroup,
  FormControl,
  Validators,
  FormBuilder,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Employee } from 'src/app/modals/employee.modal';
import { AppUser } from 'src/app/modals/user.modal';
import { EmployeeService } from 'src/app/shared/services/employee.service';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-affect-account',
  templateUrl: './affect-account.component.html',
  styleUrls: ['./affect-account.component.css'],
})
export class AffectAccountComponent implements OnInit {
  employeeId: any;
  employee: Employee = new Employee();

  submitted: boolean = false;
  appUser: AppUser = new AppUser();
  roles: string[] = ['ADMIN', 'USER', 'CHEF DEPARTEMENT'];
  selectedRole: any;

  public addAccountEmployeeForm: FormGroup = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
    role: new FormControl(''),
  });

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private employeeService: EmployeeService,
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.addAccountEmployeeForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      role: ['', Validators.required],
    });

    this.route.paramMap.subscribe((params) => {
      const stId = +params.get('employeeId')!;
      this.employeeId = stId;
    });
    this.employeeService.getEmployee(this.employeeId).subscribe((res) => {
      this.employee = res;
    });
  }

  onSubmit() {
    this.submitted = true;
    if (this.addAccountEmployeeForm.invalid) {
      return;
    }

    if (this.submitted) {
      this.userService.addUser(this.appUser, this.selectedRole).subscribe(
        (response) => {
          console.log(response);
          this.employee.appUser = response;
          this.employeeService
            .updateEmployee(this.employee)
            .subscribe((res) => {
                this.router.navigate(['users/edit-profile', this.employeeId]);
            });
        },
        (error) => {
          console.log(error);
        }
      );
    }
  }
}
