import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GeneralComponent } from '../general/general.component';
import { RouterModule, Routes } from '@angular/router';
import { NgSelectModule } from '@ng-select/ng-select';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { DemandeassuranceComponent } from './DemandeForm/demandeassurance/demandeassurance.component';
import { DemandeattestationComponent } from './DemandeForm/demandeattestation/demandeattestation.component';
import { DemandeavanceComponent } from './DemandeForm/demandeavance/demandeavance.component';
import { DemandecongeComponent } from './DemandeForm/demandeconge/demandeconge.component';
import { DemandefichedepComponent } from './DemandeForm/demandefichedep/demandefichedep.component';
import { AssuranceComponent } from './DemandeList/assurance/assurance.component';
import { AvanceComponent } from './DemandeList/avance/avance.component';
import { CongeComponent } from './DemandeList/conge/conge.component';
import { FichedepaieComponent } from './DemandeList/fichedepaie/fichedepaie.component';
import { Listeattestation1Component } from './DemandeList/listeattestation1/listeattestation1.component';
import { DemandeInspectComponent } from './DemandeInspect/demande-inspect.component';
import { PersonalDemandesComponent } from './PersonalDemandes/personal-demandes.component';
import { PersonalDemandeInspectComponent } from './PersonalDemandesInspect/personal-demande-inspect.componenet';

const routes: Routes = [
  {
    path: '',
    component: GeneralComponent,
    children: [
      {
        path: 'attestation',
        component: Listeattestation1Component,
      },
      {
        path: 'assurance',
        component: AssuranceComponent,
      },
      {
        path: 'conge',
        component: CongeComponent,
      },
      {
        path: 'avance',
        component: AvanceComponent,
      },
      {
        path: 'fichep',
        component: FichedepaieComponent,
      },
      {
        path: 'demandeavance',
        component: DemandeavanceComponent,
      },
      {
        path: 'demandefichedep',
        component: DemandefichedepComponent,
      },
      {
        path: 'demandeconge',
        component: DemandecongeComponent,
      },
      {
        path: 'demandeassurance',
        component: DemandeassuranceComponent,
      },
      {
        path: 'demandeattestation',
        component: DemandeattestationComponent,
      },
      {
        path: 'demande-details/:demandeType/:demandeId',
        component: DemandeInspectComponent,
      },
      {
        path: 'mes-demandes',
        component: PersonalDemandesComponent,
      },
      {
        path: 'ma-demande-details/:demandeType/:demandeId',
        component: PersonalDemandeInspectComponent,
      },
    ],
  },
];

@NgModule({
  declarations: [
    Listeattestation1Component,
    AvanceComponent,
    CongeComponent,
    AssuranceComponent,
    FichedepaieComponent,
    DemandeavanceComponent,
    DemandefichedepComponent,
    DemandeassuranceComponent,
    DemandecongeComponent,
    DemandeattestationComponent,
    DemandeInspectComponent,
    PersonalDemandesComponent,
    PersonalDemandeInspectComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    Ng2SmartTableModule,
    SweetAlert2Module,
    FormsModule,
    ReactiveFormsModule,
    NgSelectModule,
    FormsModule,
  ],
})
export class WebsiteModule {}
