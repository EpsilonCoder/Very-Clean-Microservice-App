import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AvisGenerauxComponent } from '../list/avis-generaux.component';
import { AvisGenerauxDetailComponent } from '../detail/avis-generaux-detail.component';
import { AvisGenerauxUpdateComponent } from '../update/avis-generaux-update.component';
import { AvisGenerauxRoutingResolveService } from './avis-generaux-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const avisGenerauxRoute: Routes = [
  {
    path: '',
    component: AvisGenerauxComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AvisGenerauxDetailComponent,
    resolve: {
      avisGeneraux: AvisGenerauxRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AvisGenerauxUpdateComponent,
    resolve: {
      avisGeneraux: AvisGenerauxRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AvisGenerauxUpdateComponent,
    resolve: {
      avisGeneraux: AvisGenerauxRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(avisGenerauxRoute)],
  exports: [RouterModule],
})
export class AvisGenerauxRoutingModule {}
