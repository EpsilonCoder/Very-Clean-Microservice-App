import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICategorieFournisseur } from '../categorie-fournisseur.model';
import { CategorieFournisseurService } from '../service/categorie-fournisseur.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './categorie-fournisseur-delete-dialog.component.html',
})
export class CategorieFournisseurDeleteDialogComponent {
  categorieFournisseur?: ICategorieFournisseur;

  constructor(protected categorieFournisseurService: CategorieFournisseurService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categorieFournisseurService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
