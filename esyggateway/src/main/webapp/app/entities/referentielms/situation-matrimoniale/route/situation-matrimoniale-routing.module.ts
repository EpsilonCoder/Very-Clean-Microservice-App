import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SituationMatrimonialeComponent } from '../list/situation-matrimoniale.component';
import { SituationMatrimonialeDetailComponent } from '../detail/situation-matrimoniale-detail.component';
import { SituationMatrimonialeUpdateComponent } from '../update/situation-matrimoniale-update.component';
import { SituationMatrimonialeRoutingResolveService } from './situation-matrimoniale-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const situationMatrimonialeRoute: Routes = [
  {
    path: '',
    component: SituationMatrimonialeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SituationMatrimonialeDetailComponent,
    resolve: {
      situationMatrimoniale: SituationMatrimonialeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SituationMatrimonialeUpdateComponent,
    resolve: {
      situationMatrimoniale: SituationMatrimonialeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SituationMatrimonialeUpdateComponent,
    resolve: {
      situationMatrimoniale: SituationMatrimonialeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(situationMatrimonialeRoute)],
  exports: [RouterModule],
})
export class SituationMatrimonialeRoutingModule {}
