import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PersonnesRessourcesComponent } from '../list/personnes-ressources.component';
import { PersonnesRessourcesDetailComponent } from '../detail/personnes-ressources-detail.component';
import { PersonnesRessourcesUpdateComponent } from '../update/personnes-ressources-update.component';
import { PersonnesRessourcesRoutingResolveService } from './personnes-ressources-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const personnesRessourcesRoute: Routes = [
  {
    path: '',
    component: PersonnesRessourcesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PersonnesRessourcesDetailComponent,
    resolve: {
      personnesRessources: PersonnesRessourcesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PersonnesRessourcesUpdateComponent,
    resolve: {
      personnesRessources: PersonnesRessourcesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PersonnesRessourcesUpdateComponent,
    resolve: {
      personnesRessources: PersonnesRessourcesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(personnesRessourcesRoute)],
  exports: [RouterModule],
})
export class PersonnesRessourcesRoutingModule {}
