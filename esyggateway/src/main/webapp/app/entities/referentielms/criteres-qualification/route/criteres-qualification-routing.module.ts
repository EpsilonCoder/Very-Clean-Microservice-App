import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CriteresQualificationComponent } from '../list/criteres-qualification.component';
import { CriteresQualificationDetailComponent } from '../detail/criteres-qualification-detail.component';
import { CriteresQualificationUpdateComponent } from '../update/criteres-qualification-update.component';
import { CriteresQualificationRoutingResolveService } from './criteres-qualification-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const criteresQualificationRoute: Routes = [
  {
    path: '',
    component: CriteresQualificationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CriteresQualificationDetailComponent,
    resolve: {
      criteresQualification: CriteresQualificationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CriteresQualificationUpdateComponent,
    resolve: {
      criteresQualification: CriteresQualificationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CriteresQualificationUpdateComponent,
    resolve: {
      criteresQualification: CriteresQualificationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(criteresQualificationRoute)],
  exports: [RouterModule],
})
export class CriteresQualificationRoutingModule {}
