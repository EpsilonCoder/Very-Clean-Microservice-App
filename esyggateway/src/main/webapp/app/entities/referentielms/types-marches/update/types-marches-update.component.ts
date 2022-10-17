import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { TypesMarchesFormService, TypesMarchesFormGroup } from './types-marches-form.service';
import { ITypesMarches } from '../types-marches.model';
import { TypesMarchesService } from '../service/types-marches.service';

@Component({
  selector: 'jhi-types-marches-update',
  templateUrl: './types-marches-update.component.html',
})
export class TypesMarchesUpdateComponent implements OnInit {
  isSaving = false;
  typesMarches: ITypesMarches | null = null;

  editForm: TypesMarchesFormGroup = this.typesMarchesFormService.createTypesMarchesFormGroup();

  constructor(
    protected typesMarchesService: TypesMarchesService,
    protected typesMarchesFormService: TypesMarchesFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typesMarches }) => {
      this.typesMarches = typesMarches;
      if (typesMarches) {
        this.updateForm(typesMarches);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typesMarches = this.typesMarchesFormService.getTypesMarches(this.editForm);
    if (typesMarches.id !== null) {
      this.subscribeToSaveResponse(this.typesMarchesService.update(typesMarches));
    } else {
      this.subscribeToSaveResponse(this.typesMarchesService.create(typesMarches));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypesMarches>>): void {
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

  protected updateForm(typesMarches: ITypesMarches): void {
    this.typesMarches = typesMarches;
    this.typesMarchesFormService.resetForm(this.editForm, typesMarches);
  }
}
