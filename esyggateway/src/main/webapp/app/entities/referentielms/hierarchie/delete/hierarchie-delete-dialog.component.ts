import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IHierarchie } from '../hierarchie.model';
import { HierarchieService } from '../service/hierarchie.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './hierarchie-delete-dialog.component.html',
})
export class HierarchieDeleteDialogComponent {
  hierarchie?: IHierarchie;

  constructor(protected hierarchieService: HierarchieService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hierarchieService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
