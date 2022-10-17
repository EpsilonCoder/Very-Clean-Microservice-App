import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { GroupesImputationFormService, GroupesImputationFormGroup } from './groupes-imputation-form.service';
import { IGroupesImputation } from '../groupes-imputation.model';
import { GroupesImputationService } from '../service/groupes-imputation.service';

@Component({
  selector: 'jhi-groupes-imputation-update',
  templateUrl: './groupes-imputation-update.component.html',
})
export class GroupesImputationUpdateComponent implements OnInit {
  isSaving = false;
  groupesImputation: IGroupesImputation | null = null;

  editForm: GroupesImputationFormGroup = this.groupesImputationFormService.createGroupesImputationFormGroup();

  constructor(
    protected groupesImputationService: GroupesImputationService,
    protected groupesImputationFormService: GroupesImputationFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ groupesImputation }) => {
      this.groupesImputation = groupesImputation;
      if (groupesImputation) {
        this.updateForm(groupesImputation);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const groupesImputation = this.groupesImputationFormService.getGroupesImputation(this.editForm);
    if (groupesImputation.id !== null) {
      this.subscribeToSaveResponse(this.groupesImputationService.update(groupesImputation));
    } else {
      this.subscribeToSaveResponse(this.groupesImputationService.create(groupesImputation));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGroupesImputation>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(groupesImputation: IGroupesImputation): void {
    this.groupesImputation = groupesImputation;
    this.groupesImputationFormService.resetForm(this.editForm, groupesImputation);
  }
}
