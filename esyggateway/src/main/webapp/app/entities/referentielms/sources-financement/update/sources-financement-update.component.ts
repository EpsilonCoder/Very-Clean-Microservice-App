import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { SourcesFinancementFormService, SourcesFinancementFormGroup } from './sources-financement-form.service';
import { ISourcesFinancement } from '../sources-financement.model';
import { SourcesFinancementService } from '../service/sources-financement.service';

@Component({
  selector: 'jhi-sources-financement-update',
  templateUrl: './sources-financement-update.component.html',
})
export class SourcesFinancementUpdateComponent implements OnInit {
  isSaving = false;
  sourcesFinancement: ISourcesFinancement | null = null;

  editForm: SourcesFinancementFormGroup = this.sourcesFinancementFormService.createSourcesFinancementFormGroup();

  constructor(
    protected sourcesFinancementService: SourcesFinancementService,
    protected sourcesFinancementFormService: SourcesFinancementFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sourcesFinancement }) => {
      this.sourcesFinancement = sourcesFinancement;
      if (sourcesFinancement) {
        this.updateForm(sourcesFinancement);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sourcesFinancement = this.sourcesFinancementFormService.getSourcesFinancement(this.editForm);
    if (sourcesFinancement.id !== null) {
      this.subscribeToSaveResponse(this.sourcesFinancementService.update(sourcesFinancement));
    } else {
      this.subscribeToSaveResponse(this.sourcesFinancementService.create(sourcesFinancement));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISourcesFinancement>>): void {
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

  protected updateForm(sourcesFinancement: ISourcesFinancement): void {
    this.sourcesFinancement = sourcesFinancement;
    this.sourcesFinancementFormService.resetForm(this.editForm, sourcesFinancement);
  }
}
