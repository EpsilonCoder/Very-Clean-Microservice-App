import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CategorieFournisseurComponent } from '../list/categorie-fournisseur.component';
import { CategorieFournisseurDetailComponent } from '../detail/categorie-fournisseur-detail.component';
import { CategorieFournisseurUpdateComponent } from '../update/categorie-fournisseur-update.component';
import { CategorieFournisseurRoutingResolveService } from './categorie-fournisseur-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const categorieFournisseurRoute: Routes = [
  {
    path: '',
    component: CategorieFournisseurComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CategorieFournisseurDetailComponent,
    resolve: {
      categorieFournisseur: CategorieFournisseurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CategorieFournisseurUpdateComponent,
    resolve: {
      categorieFournisseur: CategorieFournisseurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CategorieFournisseurUpdateComponent,
    resolve: {
      categorieFournisseur: CategorieFournisseurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(categorieFournisseurRoute)],
  exports: [RouterModule],
})
export class CategorieFournisseurRoutingModule {}
