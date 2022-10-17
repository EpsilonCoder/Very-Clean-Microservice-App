import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ModeSelectionComponent } from '../list/mode-selection.component';
import { ModeSelectionDetailComponent } from '../detail/mode-selection-detail.component';
import { ModeSelectionUpdateComponent } from '../update/mode-selection-update.component';
import { ModeSelectionRoutingResolveService } from './mode-selection-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const modeSelectionRoute: Routes = [
  {
    path: '',
    component: ModeSelectionComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ModeSelectionDetailComponent,
    resolve: {
      modeSelection: ModeSelectionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ModeSelectionUpdateComponent,
    resolve: {
      modeSelection: ModeSelectionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ModeSelectionUpdateComponent,
    resolve: {
      modeSelection: ModeSelectionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(modeSelectionRoute)],
  exports: [RouterModule],
})
export class ModeSelectionRoutingModule {}
