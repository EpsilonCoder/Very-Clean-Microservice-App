import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AutoriteContractanteComponent } from '../list/autorite-contractante.component';
import { AutoriteContractanteDetailComponent } from '../detail/autorite-contractante-detail.component';
import { AutoriteContractanteRoutingResolveService } from './autorite-contractante-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const autoriteContractanteRoute: Routes = [
  {
    path: '',
    component: AutoriteContractanteComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AutoriteContractanteDetailComponent,
    resolve: {
      autoriteContractante: AutoriteContractanteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(autoriteContractanteRoute)],
  exports: [RouterModule],
})
export class AutoriteContractanteRoutingModule {}
