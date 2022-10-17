import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { DirectionFormService, DirectionFormGroup } from './direction-form.service';
import { IDirection } from '../direction.model';
import { DirectionService } from '../service/direction.service';

@Component({
  selector: 'jhi-direction-update',
  templateUrl: './direction-update.component.html',
})
export class DirectionUpdateComponent implements OnInit {
  isSaving = false;
  direction: IDirection | null = null;

  editForm: DirectionFormGroup = this.directionFormService.createDirectionFormGroup();

  constructor(
    protected directionService: DirectionService,
    protected directionFormService: DirectionFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ direction }) => {
      this.direction = direction;
      if (direction) {
        this.updateForm(direction);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const direction = this.directionFormService.getDirection(this.editForm);
    if (direction.id !== null) {
      this.subscribeToSaveResponse(this.directionService.update(direction));
    } else {
      this.subscribeToSaveResponse(this.directionService.create(direction));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDirection>>): void {
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

  protected updateForm(direction: IDirection): void {
    this.direction = direction;
    this.directionFormService.resetForm(this.editForm, direction);
  }
}
