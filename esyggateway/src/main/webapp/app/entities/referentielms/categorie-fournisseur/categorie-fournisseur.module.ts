import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CategorieFournisseurComponent } from './list/categorie-fournisseur.component';
import { CategorieFournisseurDetailComponent } from './detail/categorie-fournisseur-detail.component';
import { CategorieFournisseurUpdateComponent } from './update/categorie-fournisseur-update.component';
import { CategorieFournisseurDeleteDialogComponent } from './delete/categorie-fournisseur-delete-dialog.component';
import { CategorieFournisseurRoutingModule } from './route/categorie-fournisseur-routing.module';

@NgModule({
  imports: [SharedModule, CategorieFournisseurRoutingModule],
  declarations: [
    CategorieFournisseurComponent,
    CategorieFournisseurDetailComponent,
    CategorieFournisseurUpdateComponent,
    CategorieFournisseurDeleteDialogComponent,
  ],
})
export class ReferentielmsCategorieFournisseurModule {}
