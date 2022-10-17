import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ConfigurationTauxComponent } from '../list/configuration-taux.component';
import { ConfigurationTauxDetailComponent } from '../detail/configuration-taux-detail.component';
import { ConfigurationTauxUpdateComponent } from '../update/configuration-taux-update.component';
import { ConfigurationTauxRoutingResolveService } from './configuration-taux-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const configurationTauxRoute: Routes = [
  {
    path: '',
    component: ConfigurationTauxComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConfigurationTauxDetailComponent,
    resolve: {
      configurationTaux: ConfigurationTauxRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConfigurationTauxUpdateComponent,
    resolve: {
      configurationTaux: ConfigurationTauxRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConfigurationTauxUpdateComponent,
    resolve: {
      configurationTaux: ConfigurationTauxRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(configurationTauxRoute)],
  exports: [RouterModule],
})
export class ConfigurationTauxRoutingModule {}
