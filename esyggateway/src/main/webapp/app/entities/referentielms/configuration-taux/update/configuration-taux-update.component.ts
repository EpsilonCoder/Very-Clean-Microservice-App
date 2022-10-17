import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ConfigurationTauxFormService, ConfigurationTauxFormGroup } from './configuration-taux-form.service';
import { IConfigurationTaux } from '../configuration-taux.model';
import { ConfigurationTauxService } from '../service/configuration-taux.service';
import { IPays } from 'app/entities/referentielms/pays/pays.model';
import { PaysService } from 'app/entities/referentielms/pays/service/pays.service';

@Component({
  selector: 'jhi-configuration-taux-update',
  templateUrl: './configuration-taux-update.component.html',
})
export class ConfigurationTauxUpdateComponent implements OnInit {
  isSaving = false;
  configurationTaux: IConfigurationTaux | null = null;

  paysSharedCollection: IPays[] = [];

  editForm: ConfigurationTauxFormGroup = this.configurationTauxFormService.createConfigurationTauxFormGroup();

  constructor(
    protected configurationTauxService: ConfigurationTauxService,
    protected configurationTauxFormService: ConfigurationTauxFormService,
    protected paysService: PaysService,
    protected activatedRoute: ActivatedRoute
  ) {}

  comparePays = (o1: IPays | null, o2: IPays | null): boolean => this.paysService.comparePays(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ configurationTaux }) => {
      this.configurationTaux = configurationTaux;
      if (configurationTaux) {
        this.updateForm(configurationTaux);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const configurationTaux = this.configurationTauxFormService.getConfigurationTaux(this.editForm);
    if (configurationTaux.id !== null) {
      this.subscribeToSaveResponse(this.configurationTauxService.update(configurationTaux));
    } else {
      this.subscribeToSaveResponse(this.configurationTauxService.create(configurationTaux));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConfigurationTaux>>): void {
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

  protected updateForm(configurationTaux: IConfigurationTaux): void {
    this.configurationTaux = configurationTaux;
    this.configurationTauxFormService.resetForm(this.editForm, configurationTaux);

    this.paysSharedCollection = this.paysService.addPaysToCollectionIfMissing<IPays>(this.paysSharedCollection, configurationTaux.pays);
  }

  protected loadRelationshipsOptions(): void {
    this.paysService
      .query()
      .pipe(map((res: HttpResponse<IPays[]>) => res.body ?? []))
      .pipe(map((pays: IPays[]) => this.paysService.addPaysToCollectionIfMissing<IPays>(pays, this.configurationTaux?.pays)))
      .subscribe((pays: IPays[]) => (this.paysSharedCollection = pays));
  }
}
