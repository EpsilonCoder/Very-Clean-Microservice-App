import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PersonnesRessourcesFormService, PersonnesRessourcesFormGroup } from './personnes-ressources-form.service';
import { IPersonnesRessources } from '../personnes-ressources.model';
import { PersonnesRessourcesService } from '../service/personnes-ressources.service';

@Component({
  selector: 'jhi-personnes-ressources-update',
  templateUrl: './personnes-ressources-update.component.html',
})
export class PersonnesRessourcesUpdateComponent implements OnInit {
  isSaving = false;
  personnesRessources: IPersonnesRessources | null = null;

  editForm: PersonnesRessourcesFormGroup = this.personnesRessourcesFormService.createPersonnesRessourcesFormGroup();

  constructor(
    protected personnesRessourcesService: PersonnesRessourcesService,
    protected personnesRessourcesFormService: PersonnesRessourcesFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personnesRessources }) => {
      this.personnesRessources = personnesRessources;
      if (personnesRessources) {
        this.updateForm(personnesRessources);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const personnesRessources = this.personnesRessourcesFormService.getPersonnesRessources(this.editForm);
    if (personnesRessources.id !== null) {
      this.subscribeToSaveResponse(this.personnesRessourcesService.update(personnesRessources));
    } else {
      this.subscribeToSaveResponse(this.personnesRessourcesService.create(personnesRessources));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonnesRessources>>): void {
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

  protected updateForm(personnesRessources: IPersonnesRessources): void {
    this.personnesRessources = personnesRessources;
    this.personnesRessourcesFormService.resetForm(this.editForm, personnesRessources);
  }
}
