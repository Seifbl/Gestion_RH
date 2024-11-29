import {
  Component,
  OnInit,
  ViewChild,
  ElementRef,
  Inject,
  Renderer2,
} from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { Router } from '@angular/router';
import { TokenStorageService } from '../../../core/guard/token-storage.service';
import { AuthService } from 'src/app/shared/services/auth.service';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
  currentUser: any;
  constructor(
    private tokenStorageService: TokenStorageService,
    @Inject(DOCUMENT) private document: Document,
    private renderer: Renderer2,
    private router: Router,
    private authService: AuthService,
    private userService: UserService
  ) {}
  employeeId: any;
  ngOnInit(): void {
    this.currentUser = this.tokenStorageService.getUser();
    this.userService.getUserProperties().subscribe((res1) => {
      this.userService.getUserInfo(res1.userId).subscribe((res2) => {
        this.employeeId = res2.employeeId;
      });
    });
  }

  /**
   * Sidebar toggle on hamburger button click
   */
  toggleSidebar(e: Event) {
    e.preventDefault();
    this.document.body.classList.toggle('sidebar-open');
  }

  goToUserProfile() {
    this.router.navigate(['users/profil', this.employeeId]);
  }
  /**
   * Logout
   */
  onLogout(e: Event) {
    e.preventDefault();
    this.authService.logout();
    this.router.navigate(['/auth/login']);
  }

  changePassword() {
    this.router.navigate(['users/change-password', this.employeeId]);
  }
}
