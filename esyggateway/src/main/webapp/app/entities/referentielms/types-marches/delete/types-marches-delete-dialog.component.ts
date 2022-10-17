import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypesMarches } from '../types-marches.model';
import { TypesMarchesService } from '../service/types-marches.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './types-marches-delete-dialog.component.html',
})
export class TypesMarchesDeleteDialogComponent {
  typesMarches?: ITypesMarches;

  constructor(protected typesMarchesService: TypesMarchesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typesMarchesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
