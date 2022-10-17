import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SourcesFinancementComponent } from '../list/sources-financement.component';
import { SourcesFinancementDetailComponent } from '../detail/sources-financement-detail.component';
import { SourcesFinancementUpdateComponent } from '../update/sources-financement-update.component';
import { SourcesFinancementRoutingResolveService } from './sources-financement-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const sourcesFinancementRoute: Routes = [
  {
    path: '',
    component: SourcesFinancementComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SourcesFinancementDetailComponent,
    resolve: {
      sourcesFinancement: SourcesFinancementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SourcesFinancementUpdateComponent,
    resolve: {
      sourcesFinancement: SourcesFinancementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SourcesFinancementUpdateComponent,
    resolve: {
      sourcesFinancement: SourcesFinancementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(sourcesFinancementRoute)],
  exports: [RouterModule],
})
export class SourcesFinancementRoutingModule {}
