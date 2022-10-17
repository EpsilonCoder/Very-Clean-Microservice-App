import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SygAutoriteContractanteComponent } from '../list/syg-autorite-contractante.component';
import { SygAutoriteContractanteDetailComponent } from '../detail/syg-autorite-contractante-detail.component';
import { SygAutoriteContractanteUpdateComponent } from '../update/syg-autorite-contractante-update.component';
import { SygAutoriteContractanteRoutingResolveService } from './syg-autorite-contractante-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const sygAutoriteContractanteRoute: Routes = [
  {
    path: '',
    component: SygAutoriteContractanteComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SygAutoriteContractanteDetailComponent,
    resolve: {
      sygAutoriteContractante: SygAutoriteContractanteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SygAutoriteContractanteUpdateComponent,
    resolve: {
      sygAutoriteContractante: SygAutoriteContractanteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SygAutoriteContractanteUpdateComponent,
    resolve: {
      sygAutoriteContractante: SygAutoriteContractanteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(sygAutoriteContractanteRoute)],
  exports: [RouterModule],
})
export class SygAutoriteContractanteRoutingModule {}
