import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { TokenStorageService } from '../../../../core/guard/token-storage.service';
import { AuthService } from '../../../../shared/services/auth.service';
import { AuthCredentials } from 'src/app/modals/auth.modal';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  form: any = {};
  token: string;
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  returnUrl: any;
  authCred = new AuthCredentials();
  userRole: any;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    // get return url from route parameters or default to '/'
    // this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }
  getallroles() {}
  getallmodules() {}

  onLoggedin() {
    this.authCred.username = this.form.username;
    this.authCred.password = this.form.password;

    this.authService.login(this.authCred).subscribe(
      (response) => {
        const token = response.token;
        this.authService.saveToken(token);
        this.userService.getUserProperties().subscribe((response) => {
          this.userRole = response.authorities[0].authority;
          switch (this.userRole) {
            case 'ADMIN':
              this.router.navigate(['/dashboard']);
              break;
            case 'CHEF DEPARTEMENT':
              this.router.navigate(['/dashboard']);
              break;
            case 'USER':
              this.router.navigate(['/website/mes-demandes']);
          }
        });
      },
      (error) => {
        console.log(error);
      }
    );
  }

  logout() {}
}
