import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ListeuserComponent } from './listeuser/listeuser.component';
import { AdduserComponent } from './adduser/adduser.component';
import { UpdateuserComponent } from './updateuser/updateuser.component';
import { RouterModule, Routes } from '@angular/router';
import { GeneralComponent } from '../general/general.component';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgSelectModule } from '@ng-select/ng-select';
import { EditProfileComponent } from './edit-profile/edit-profile.component';
import { ProfileComponent } from './profile/profile.component';
import { ChangePasswordComponent } from './changepassword/change-password.component';
import { AffectAccountComponent } from './affect-account/affect-account.component';
import { AbsencePage } from './absence-page/absence-page.component';

const routes: Routes = [
  {
    path: '',
    component: GeneralComponent,
    children: [
      {
        path: 'change-password/:employeeId',
        component: ChangePasswordComponent,
      },
      {
        path: 'profil/:employeeId',
        component: ProfileComponent,
      },
      {
        path: 'edit-profile/:employeeId',
        component: EditProfileComponent,
      },

      {
        path: 'list-user',
        component: ListeuserComponent,
      },
      {
        path: 'add-user',
        component: AdduserComponent,
      },
      {
        path: 'update-user/:id',
        component: UpdateuserComponent,
      },
      {
        path: 'affect-account/:employeeId',
        component: AffectAccountComponent,
      },
      {
        path: 'user-absence/:employeeId',
        component: AbsencePage,
      },
    ],
  },
];

@NgModule({
  declarations: [
    ListeuserComponent,
    AdduserComponent,
    UpdateuserComponent,
    EditProfileComponent,
    ProfileComponent,
    ChangePasswordComponent,
    AffectAccountComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    Ng2SmartTableModule,
    SweetAlert2Module,
    FormsModule,
    ReactiveFormsModule,
    NgSelectModule,
  ],
})
export class UserModule {}
