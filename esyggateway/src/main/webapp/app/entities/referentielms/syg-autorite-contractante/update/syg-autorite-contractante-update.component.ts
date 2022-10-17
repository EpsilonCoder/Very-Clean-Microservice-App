import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { SygAutoriteContractanteFormService, SygAutoriteContractanteFormGroup } from './syg-autorite-contractante-form.service';
import { ISygAutoriteContractante } from '../syg-autorite-contractante.model';
import { SygAutoriteContractanteService } from '../service/syg-autorite-contractante.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ITypeAutoriteContractante } from 'app/entities/referentielms/type-autorite-contractante/type-autorite-contractante.model';
import { TypeAutoriteContractanteService } from 'app/entities/referentielms/type-autorite-contractante/service/type-autorite-contractante.service';

@Component({
  selector: 'jhi-syg-autorite-contractante-update',
  templateUrl: './syg-autorite-contractante-update.component.html',
})
export class SygAutoriteContractanteUpdateComponent implements OnInit {
  isSaving = false;
  sygAutoriteContractante: ISygAutoriteContractante | null = null;

  typeAutoriteContractantesSharedCollection: ITypeAutoriteContractante[] = [];

  editForm: SygAutoriteContractanteFormGroup = this.sygAutoriteContractanteFormService.createSygAutoriteContractanteFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected sygAutoriteContractanteService: SygAutoriteContractanteService,
    protected sygAutoriteContractanteFormService: SygAutoriteContractanteFormService,
    protected typeAutoriteContractanteService: TypeAutoriteContractanteService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareTypeAutoriteContractante = (o1: ITypeAutoriteContractante | null, o2: ITypeAutoriteContractante | null): boolean =>
    this.typeAutoriteContractanteService.compareTypeAutoriteContractante(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sygAutoriteContractante }) => {
      this.sygAutoriteContractante = sygAutoriteContractante;
      if (sygAutoriteContractante) {
        this.updateForm(sygAutoriteContractante);
      }

      this.loadRelationshipsOptions();
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

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sygAutoriteContractante = this.sygAutoriteContractanteFormService.getSygAutoriteContractante(this.editForm);
    if (sygAutoriteContractante.id !== null) {
      this.subscribeToSaveResponse(this.sygAutoriteContractanteService.update(sygAutoriteContractante));
    } else {
      this.subscribeToSaveResponse(this.sygAutoriteContractanteService.create(sygAutoriteContractante));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISygAutoriteContractante>>): void {
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

  protected updateForm(sygAutoriteContractante: ISygAutoriteContractante): void {
    this.sygAutoriteContractante = sygAutoriteContractante;
    this.sygAutoriteContractanteFormService.resetForm(this.editForm, sygAutoriteContractante);

    this.typeAutoriteContractantesSharedCollection =
      this.typeAutoriteContractanteService.addTypeAutoriteContractanteToCollectionIfMissing<ITypeAutoriteContractante>(
        this.typeAutoriteContractantesSharedCollection,
        sygAutoriteContractante.typeAutoriteContractante
      );
  }

  protected loadRelationshipsOptions(): void {
    this.typeAutoriteContractanteService
      .query()
      .pipe(map((res: HttpResponse<ITypeAutoriteContractante[]>) => res.body ?? []))
      .pipe(
        map((typeAutoriteContractantes: ITypeAutoriteContractante[]) =>
          this.typeAutoriteContractanteService.addTypeAutoriteContractanteToCollectionIfMissing<ITypeAutoriteContractante>(
            typeAutoriteContractantes,
            this.sygAutoriteContractante?.typeAutoriteContractante
          )
        )
      )
      .subscribe(
        (typeAutoriteContractantes: ITypeAutoriteContractante[]) =>
          (this.typeAutoriteContractantesSharedCollection = typeAutoriteContractantes)
      );
  }
}
