import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { SpecialitesPersonnelFormService, SpecialitesPersonnelFormGroup } from './specialites-personnel-form.service';
import { ISpecialitesPersonnel } from '../specialites-personnel.model';
import { SpecialitesPersonnelService } from '../service/specialites-personnel.service';

@Component({
  selector: 'jhi-specialites-personnel-update',
  templateUrl: './specialites-personnel-update.component.html',
})
export class SpecialitesPersonnelUpdateComponent implements OnInit {
  isSaving = false;
  specialitesPersonnel: ISpecialitesPersonnel | null = null;

  editForm: SpecialitesPersonnelFormGroup = this.specialitesPersonnelFormService.createSpecialitesPersonnelFormGroup();

  constructor(
    protected specialitesPersonnelService: SpecialitesPersonnelService,
    protected specialitesPersonnelFormService: SpecialitesPersonnelFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ specialitesPersonnel }) => {
      this.specialitesPersonnel = specialitesPersonnel;
      if (specialitesPersonnel) {
        this.updateForm(specialitesPersonnel);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const specialitesPersonnel = this.specialitesPersonnelFormService.getSpecialitesPersonnel(this.editForm);
    if (specialitesPersonnel.id !== null) {
      this.subscribeToSaveResponse(this.specialitesPersonnelService.update(specialitesPersonnel));
    } else {
      this.subscribeToSaveResponse(this.specialitesPersonnelService.create(specialitesPersonnel));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpecialitesPersonnel>>): void {
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

  protected updateForm(specialitesPersonnel: ISpecialitesPersonnel): void {
    this.specialitesPersonnel = specialitesPersonnel;
    this.specialitesPersonnelFormService.resetForm(this.editForm, specialitesPersonnel);
  }
}
