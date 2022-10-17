import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISygAutoriteContractante } from '../syg-autorite-contractante.model';
import { SygAutoriteContractanteService } from '../service/syg-autorite-contractante.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './syg-autorite-contractante-delete-dialog.component.html',
})
export class SygAutoriteContractanteDeleteDialogComponent {
  sygAutoriteContractante?: ISygAutoriteContractante;

  constructor(protected sygAutoriteContractanteService: SygAutoriteContractanteService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sygAutoriteContractanteService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
