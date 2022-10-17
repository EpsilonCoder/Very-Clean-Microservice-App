import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TypesMarchesComponent } from '../list/types-marches.component';
import { TypesMarchesDetailComponent } from '../detail/types-marches-detail.component';
import { TypesMarchesUpdateComponent } from '../update/types-marches-update.component';
import { TypesMarchesRoutingResolveService } from './types-marches-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const typesMarchesRoute: Routes = [
  {
    path: '',
    component: TypesMarchesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TypesMarchesDetailComponent,
    resolve: {
      typesMarches: TypesMarchesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TypesMarchesUpdateComponent,
    resolve: {
      typesMarches: TypesMarchesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TypesMarchesUpdateComponent,
    resolve: {
      typesMarches: TypesMarchesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(typesMarchesRoute)],
  exports: [RouterModule],
})
export class TypesMarchesRoutingModule {}
