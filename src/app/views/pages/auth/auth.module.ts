import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { Routes, RouterModule } from '@angular/router';
import { AuthComponent } from './auth.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';


const routes: Routes = [
  {
    path: '',
    component: AuthComponent,
    children: [
      {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full'
      },
      {
        path: 'login',
        component: LoginComponent
      },
   
    ]
  },
]

@NgModule({
  declarations: [
    LoginComponent,
     AuthComponent],
  imports: [
    CommonModule,
    FormsModule, ReactiveFormsModule,NgbModule,
    RouterModule.forChild(routes)
  ]
})
export class AuthModule { }
