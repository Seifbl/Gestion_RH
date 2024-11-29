import { Injectable } from '@angular/core';
import { CanActivate, RouterStateSnapshot, ActivatedRouteSnapshot } from '@angular/router';
import { Router } from '@angular/router';
import {TokenStorageService} from "./token-storage.service";

@Injectable()
export class AuthGuard implements CanActivate {
  constructor(private router: Router, private tokenStorageService: TokenStorageService ) {}


  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

    if (this.tokenStorageService.getToken()) {
      // logged in so return true
      return true;
    }else {
      // not logged in so redirect to login page with the return url
      this.router.navigate(['/auth/login'], { queryParams: { returnUrl: state.url } });
      return false;
    }

  }
}
