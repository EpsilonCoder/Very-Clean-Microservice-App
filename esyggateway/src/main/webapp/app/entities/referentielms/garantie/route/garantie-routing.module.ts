import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GarantieComponent } from '../list/garantie.component';
import { GarantieDetailComponent } from '../detail/garantie-detail.component';
import { GarantieUpdateComponent } from '../update/garantie-update.component';
import { GarantieRoutingResolveService } from './garantie-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const garantieRoute: Routes = [
  {
    path: '',
    component: GarantieComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GarantieDetailComponent,
    resolve: {
      garantie: GarantieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GarantieUpdateComponent,
    resolve: {
      garantie: GarantieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GarantieUpdateComponent,
    resolve: {
      garantie: GarantieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(garantieRoute)],
  exports: [RouterModule],
})
export class GarantieRoutingModule {}
