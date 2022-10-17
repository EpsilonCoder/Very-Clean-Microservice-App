import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISpecialitesPersonnel } from '../specialites-personnel.model';
import { SpecialitesPersonnelService } from '../service/specialites-personnel.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './specialites-personnel-delete-dialog.component.html',
})
export class SpecialitesPersonnelDeleteDialogComponent {
  specialitesPersonnel?: ISpecialitesPersonnel;

  constructor(protected specialitesPersonnelService: SpecialitesPersonnelService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.specialitesPersonnelService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
