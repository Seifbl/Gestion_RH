import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { NgSelectModule } from '@ng-select/ng-select';
import { UpdatemoduleComponent } from './updatemodule/updatemodule.component';
import { AddmoduleComponent } from './addmodule/addmodule.component';
import { ListmoduleComponent } from './listmodule/listmodule.component';
import { RouterModule, Routes } from '@angular/router';
import { GeneralComponent } from '../general/general.component';
import { BrowserModule } from '@angular/platform-browser';
import { ChatComponent } from './chat/chat.component';

const routes: Routes = [
  {
    path: '',
    component: GeneralComponent,
    children: [
      {
        path: 'list-modules',
        component: ListmoduleComponent,
      },
      {
        path: 'add-module',
        component: AddmoduleComponent,
      },
      {
        path: 'update-module/:departementId',
        component: UpdatemoduleComponent,
      },
      {
        path: 'chat',
        component: ChatComponent,
      },
    ],
  },
];
@NgModule({
  declarations: [
    UpdatemoduleComponent,
    AddmoduleComponent,
    ListmoduleComponent,
    ChatComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule,
    ReactiveFormsModule,
    NgSelectModule,
    Ng2SmartTableModule,
    SweetAlert2Module,
  ],
})
export class EntrepriseModule {}
