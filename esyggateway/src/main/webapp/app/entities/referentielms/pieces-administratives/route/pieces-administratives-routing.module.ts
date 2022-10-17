import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PiecesAdministrativesComponent } from '../list/pieces-administratives.component';
import { PiecesAdministrativesDetailComponent } from '../detail/pieces-administratives-detail.component';
import { PiecesAdministrativesUpdateComponent } from '../update/pieces-administratives-update.component';
import { PiecesAdministrativesRoutingResolveService } from './pieces-administratives-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const piecesAdministrativesRoute: Routes = [
  {
    path: '',
    component: PiecesAdministrativesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PiecesAdministrativesDetailComponent,
    resolve: {
      piecesAdministratives: PiecesAdministrativesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PiecesAdministrativesUpdateComponent,
    resolve: {
      piecesAdministratives: PiecesAdministrativesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PiecesAdministrativesUpdateComponent,
    resolve: {
      piecesAdministratives: PiecesAdministrativesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(piecesAdministrativesRoute)],
  exports: [RouterModule],
})
export class PiecesAdministrativesRoutingModule {}
