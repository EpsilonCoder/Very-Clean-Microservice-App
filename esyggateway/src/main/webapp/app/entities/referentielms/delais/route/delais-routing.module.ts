import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DelaisComponent } from '../list/delais.component';
import { DelaisDetailComponent } from '../detail/delais-detail.component';
import { DelaisUpdateComponent } from '../update/delais-update.component';
import { DelaisRoutingResolveService } from './delais-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const delaisRoute: Routes = [
  {
    path: '',
    component: DelaisComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DelaisDetailComponent,
    resolve: {
      delais: DelaisRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DelaisUpdateComponent,
    resolve: {
      delais: DelaisRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DelaisUpdateComponent,
    resolve: {
      delais: DelaisRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(delaisRoute)],
  exports: [RouterModule],
})
export class DelaisRoutingModule {}
