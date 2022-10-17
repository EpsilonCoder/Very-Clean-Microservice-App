import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AvisGenerauxFormService, AvisGenerauxFormGroup } from './avis-generaux-form.service';
import { IAvisGeneraux } from '../avis-generaux.model';
import { AvisGenerauxService } from '../service/avis-generaux.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-avis-generaux-update',
  templateUrl: './avis-generaux-update.component.html',
})
export class AvisGenerauxUpdateComponent implements OnInit {
  isSaving = false;
  avisGeneraux: IAvisGeneraux | null = null;

  editForm: AvisGenerauxFormGroup = this.avisGenerauxFormService.createAvisGenerauxFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected avisGenerauxService: AvisGenerauxService,
    protected avisGenerauxFormService: AvisGenerauxFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avisGeneraux }) => {
      this.avisGeneraux = avisGeneraux;
      if (avisGeneraux) {
        this.updateForm(avisGeneraux);
      }
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('esyggatewayApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const avisGeneraux = this.avisGenerauxFormService.getAvisGeneraux(this.editForm);
    if (avisGeneraux.id !== null) {
      this.subscribeToSaveResponse(this.avisGenerauxService.update(avisGeneraux));
    } else {
      this.subscribeToSaveResponse(this.avisGenerauxService.create(avisGeneraux));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAvisGeneraux>>): void {
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

  protected updateForm(avisGeneraux: IAvisGeneraux): void {
    this.avisGeneraux = avisGeneraux;
    this.avisGenerauxFormService.resetForm(this.editForm, avisGeneraux);
  }
}
