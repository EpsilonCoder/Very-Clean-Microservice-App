import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { JoursFeriesComponent } from '../list/jours-feries.component';
import { JoursFeriesDetailComponent } from '../detail/jours-feries-detail.component';
import { JoursFeriesUpdateComponent } from '../update/jours-feries-update.component';
import { JoursFeriesRoutingResolveService } from './jours-feries-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const joursFeriesRoute: Routes = [
  {
    path: '',
    component: JoursFeriesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: JoursFeriesDetailComponent,
    resolve: {
      joursFeries: JoursFeriesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: JoursFeriesUpdateComponent,
    resolve: {
      joursFeries: JoursFeriesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: JoursFeriesUpdateComponent,
    resolve: {
      joursFeries: JoursFeriesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(joursFeriesRoute)],
  exports: [RouterModule],
})
export class JoursFeriesRoutingModule {}
