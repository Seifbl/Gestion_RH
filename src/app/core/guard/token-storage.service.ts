import { Injectable } from '@angular/core';

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';
const ROLES_KEY = 'auth-role';
const MODULES_KEY = 'auth-module';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
  constructor() { }

  signOut(): void {
    window.sessionStorage.clear();
  }

  public saveToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string | null {
    return window.sessionStorage.getItem(TOKEN_KEY);
  }

  public saveUser(user: any): void {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser(): any {
    const user = window.sessionStorage.getItem(USER_KEY);
    if (user) {
      return JSON.parse(user);
    }
    return {};
  }
  public saveRole(role: any): void {
    window.sessionStorage.removeItem(ROLES_KEY);
    window.sessionStorage.setItem(ROLES_KEY, JSON.stringify(role));
  }
  public getRole(): any {
    const role = window.sessionStorage.getItem(ROLES_KEY);
    if (role) {
      return JSON.parse(role);
    }
    return {};
  }

  public saveModule(role: any): void {
    window.sessionStorage.removeItem(MODULES_KEY);
    window.sessionStorage.setItem(MODULES_KEY, JSON.stringify(role));
  }
  public getModule(): any {
    const role = window.sessionStorage.getItem(MODULES_KEY);
    if (role) {
      return JSON.parse(role);
    }
    return {};
  }
}
