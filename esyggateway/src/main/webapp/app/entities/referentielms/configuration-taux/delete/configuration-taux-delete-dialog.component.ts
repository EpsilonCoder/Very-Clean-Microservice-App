import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IConfigurationTaux } from '../configuration-taux.model';
import { ConfigurationTauxService } from '../service/configuration-taux.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './configuration-taux-delete-dialog.component.html',
})
export class ConfigurationTauxDeleteDialogComponent {
  configurationTaux?: IConfigurationTaux;

  constructor(protected configurationTauxService: ConfigurationTauxService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.configurationTauxService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
