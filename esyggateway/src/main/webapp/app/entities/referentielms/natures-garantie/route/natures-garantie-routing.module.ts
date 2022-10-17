import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NaturesGarantieComponent } from '../list/natures-garantie.component';
import { NaturesGarantieDetailComponent } from '../detail/natures-garantie-detail.component';
import { NaturesGarantieUpdateComponent } from '../update/natures-garantie-update.component';
import { NaturesGarantieRoutingResolveService } from './natures-garantie-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const naturesGarantieRoute: Routes = [
  {
    path: '',
    component: NaturesGarantieComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NaturesGarantieDetailComponent,
    resolve: {
      naturesGarantie: NaturesGarantieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NaturesGarantieUpdateComponent,
    resolve: {
      naturesGarantie: NaturesGarantieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NaturesGarantieUpdateComponent,
    resolve: {
      naturesGarantie: NaturesGarantieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(naturesGarantieRoute)],
  exports: [RouterModule],
})
export class NaturesGarantieRoutingModule {}
