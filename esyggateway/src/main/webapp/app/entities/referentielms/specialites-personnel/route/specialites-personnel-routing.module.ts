import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SpecialitesPersonnelComponent } from '../list/specialites-personnel.component';
import { SpecialitesPersonnelDetailComponent } from '../detail/specialites-personnel-detail.component';
import { SpecialitesPersonnelUpdateComponent } from '../update/specialites-personnel-update.component';
import { SpecialitesPersonnelRoutingResolveService } from './specialites-personnel-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const specialitesPersonnelRoute: Routes = [
  {
    path: '',
    component: SpecialitesPersonnelComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SpecialitesPersonnelDetailComponent,
    resolve: {
      specialitesPersonnel: SpecialitesPersonnelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SpecialitesPersonnelUpdateComponent,
    resolve: {
      specialitesPersonnel: SpecialitesPersonnelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SpecialitesPersonnelUpdateComponent,
    resolve: {
      specialitesPersonnel: SpecialitesPersonnelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(specialitesPersonnelRoute)],
  exports: [RouterModule],
})
export class SpecialitesPersonnelRoutingModule {}
