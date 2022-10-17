import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { CriteresQualificationFormService, CriteresQualificationFormGroup } from './criteres-qualification-form.service';
import { ICriteresQualification } from '../criteres-qualification.model';
import { CriteresQualificationService } from '../service/criteres-qualification.service';

@Component({
  selector: 'jhi-criteres-qualification-update',
  templateUrl: './criteres-qualification-update.component.html',
})
export class CriteresQualificationUpdateComponent implements OnInit {
  isSaving = false;
  criteresQualification: ICriteresQualification | null = null;

  editForm: CriteresQualificationFormGroup = this.criteresQualificationFormService.createCriteresQualificationFormGroup();

  constructor(
    protected criteresQualificationService: CriteresQualificationService,
    protected criteresQualificationFormService: CriteresQualificationFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ criteresQualification }) => {
      this.criteresQualification = criteresQualification;
      if (criteresQualification) {
        this.updateForm(criteresQualification);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const criteresQualification = this.criteresQualificationFormService.getCriteresQualification(this.editForm);
    if (criteresQualification.id !== null) {
      this.subscribeToSaveResponse(this.criteresQualificationService.update(criteresQualification));
    } else {
      this.subscribeToSaveResponse(this.criteresQualificationService.create(criteresQualification));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICriteresQualification>>): void {
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

  protected updateForm(criteresQualification: ICriteresQualification): void {
    this.criteresQualification = criteresQualification;
    this.criteresQualificationFormService.resetForm(this.editForm, criteresQualification);
  }
}
