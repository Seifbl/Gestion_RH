import { Component, OnInit } from '@angular/core';
import {
  FormGroup,
  FormControl,
  FormBuilder,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Employee } from 'src/app/modals/employee.modal';
import { PassowrdChangeCredentials } from 'src/app/modals/passwordchange.modal';
import { AuthService } from 'src/app/shared/services/auth.service';
import { EmployeeService } from 'src/app/shared/services/employee.service';
import { UserService } from 'src/app/shared/services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css'],
})
export class ChangePasswordComponent implements OnInit {
  isAuthorized: boolean = false;
  userType: string;
  loggedInUserId: any;
  employeeId: any;

  submitted: boolean = false;
  credentials: PassowrdChangeCredentials = new PassowrdChangeCredentials();
  confirmNewUserPassword: any;

  public changePasswordForm: FormGroup = new FormGroup({
    newPassword: new FormControl(''),
    oldPassword: new FormControl(''),
    confirmNewPassword: new FormControl(''),
  });

  repeatPassword: string;

  constructor(
    private route: ActivatedRoute,
    private employeeService: EmployeeService,
    private router: Router,
    private userService: UserService,
    private authService: AuthService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.changePasswordForm = this.fb.group({
      newPassword: ['', Validators.required],
      oldPassword: ['', Validators.required],
      confirmNewPassword: ['', Validators.required],
    });

    this.userService.getUserProperties().subscribe((res1) => {
      this.userType = res1.authorities[0].authority;

      this.userService.getUserInfo(res1.userId).subscribe((res2) => {
        this.loggedInUserId = res2.employeeId;
      });

      this.route.paramMap.subscribe((params) => {
        const stId = +params.get('employeeId')!;
        this.employeeId = stId;
      });
      if (this.loggedInUserId === this.employeeId) {
        this.isAuthorized = true;
      }
    });
  }

  onSubmit() {
    this.submitted = true;
    if (this.changePasswordForm.invalid) {
      return;
    }

    if (this.submitted) {
      if (this.credentials.newPassword !== this.confirmNewUserPassword) {
        Swal.fire({
          icon: 'error',
          title: 'Erreur...',
          text: 'Les mots de passe ne correspondent pas!',
        });
      } else {
        this.userService
          .changePassword(
            this.credentials.oldPassword,
            this.credentials.newPassword
          )
          .subscribe((res) => {
            if (res === false) {
              Swal.fire({
                icon: 'error',
                title: 'Erreur...',
                text: 'Ancien mot de passe incorrect!',
              });
            } else {
              this.authService.logout();
              this.router.navigate(['/auth/login']);
            }
          });
      }
    }
  }
}
