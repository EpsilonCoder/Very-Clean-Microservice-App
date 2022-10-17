import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GroupesImputationComponent } from '../list/groupes-imputation.component';
import { GroupesImputationDetailComponent } from '../detail/groupes-imputation-detail.component';
import { GroupesImputationUpdateComponent } from '../update/groupes-imputation-update.component';
import { GroupesImputationRoutingResolveService } from './groupes-imputation-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const groupesImputationRoute: Routes = [
  {
    path: '',
    component: GroupesImputationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GroupesImputationDetailComponent,
    resolve: {
      groupesImputation: GroupesImputationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GroupesImputationUpdateComponent,
    resolve: {
      groupesImputation: GroupesImputationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GroupesImputationUpdateComponent,
    resolve: {
      groupesImputation: GroupesImputationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(groupesImputationRoute)],
  exports: [RouterModule],
})
export class GroupesImputationRoutingModule {}
