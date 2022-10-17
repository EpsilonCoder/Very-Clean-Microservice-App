import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPersonnesRessources } from '../personnes-ressources.model';
import { PersonnesRessourcesService } from '../service/personnes-ressources.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './personnes-ressources-delete-dialog.component.html',
})
export class PersonnesRessourcesDeleteDialogComponent {
  personnesRessources?: IPersonnesRessources;

  constructor(protected personnesRessourcesService: PersonnesRessourcesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.personnesRessourcesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
