import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAvisGeneraux, NewAvisGeneraux } from '../avis-generaux.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAvisGeneraux for edit and NewAvisGenerauxFormGroupInput for create.
 */
type AvisGenerauxFormGroupInput = IAvisGeneraux | PartialWithRequiredKeyOf<NewAvisGeneraux>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAvisGeneraux | NewAvisGeneraux> = Omit<T, 'datePublication'> & {
  datePublication?: string | null;
};

type AvisGenerauxFormRawValue = FormValueOf<IAvisGeneraux>;

type NewAvisGenerauxFormRawValue = FormValueOf<NewAvisGeneraux>;

type AvisGenerauxFormDefaults = Pick<NewAvisGeneraux, 'id' | 'datePublication'>;

type AvisGenerauxFormGroupContent = {
  id: FormControl<AvisGenerauxFormRawValue['id'] | NewAvisGeneraux['id']>;
  numero: FormControl<AvisGenerauxFormRawValue['numero']>;
  annee: FormControl<AvisGenerauxFormRawValue['annee']>;
  description: FormControl<AvisGenerauxFormRawValue['description']>;
  fichierAvis: FormControl<AvisGenerauxFormRawValue['fichierAvis']>;
  fichierAvisContentType: FormControl<AvisGenerauxFormRawValue['fichierAvisContentType']>;
  version: FormControl<AvisGenerauxFormRawValue['version']>;
  lastVersionValid: FormControl<AvisGenerauxFormRawValue['lastVersionValid']>;
  etat: FormControl<AvisGenerauxFormRawValue['etat']>;
  datePublication: FormControl<AvisGenerauxFormRawValue['datePublication']>;
};

export type AvisGenerauxFormGroup = FormGroup<AvisGenerauxFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AvisGenerauxFormService {
  createAvisGenerauxFormGroup(avisGeneraux: AvisGenerauxFormGroupInput = { id: null }): AvisGenerauxFormGroup {
    const avisGenerauxRawValue = this.convertAvisGenerauxToAvisGenerauxRawValue({
      ...this.getFormDefaults(),
      ...avisGeneraux,
    });
    return new FormGroup<AvisGenerauxFormGroupContent>({
      id: new FormControl(
        { value: avisGenerauxRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      numero: new FormControl(avisGenerauxRawValue.numero),
      annee: new FormControl(avisGenerauxRawValue.annee),
      description: new FormControl(avisGenerauxRawValue.description),
      fichierAvis: new FormControl(avisGenerauxRawValue.fichierAvis),
      fichierAvisContentType: new FormControl(avisGenerauxRawValue.fichierAvisContentType),
      version: new FormControl(avisGenerauxRawValue.version),
      lastVersionValid: new FormControl(avisGenerauxRawValue.lastVersionValid),
      etat: new FormControl(avisGenerauxRawValue.etat),
      datePublication: new FormControl(avisGenerauxRawValue.datePublication),
    });
  }

  getAvisGeneraux(form: AvisGenerauxFormGroup): IAvisGeneraux | NewAvisGeneraux {
    return this.convertAvisGenerauxRawValueToAvisGeneraux(form.getRawValue() as AvisGenerauxFormRawValue | NewAvisGenerauxFormRawValue);
  }

  resetForm(form: AvisGenerauxFormGroup, avisGeneraux: AvisGenerauxFormGroupInput): void {
    const avisGenerauxRawValue = this.convertAvisGenerauxToAvisGenerauxRawValue({ ...this.getFormDefaults(), ...avisGeneraux });
    form.reset(
      {
        ...avisGenerauxRawValue,
        id: { value: avisGenerauxRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AvisGenerauxFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      datePublication: currentTime,
    };
  }

  private convertAvisGenerauxRawValueToAvisGeneraux(
    rawAvisGeneraux: AvisGenerauxFormRawValue | NewAvisGenerauxFormRawValue
  ): IAvisGeneraux | NewAvisGeneraux {
    return {
      ...rawAvisGeneraux,
      datePublication: dayjs(rawAvisGeneraux.datePublication, DATE_TIME_FORMAT),
    };
  }

  private convertAvisGenerauxToAvisGenerauxRawValue(
    avisGeneraux: IAvisGeneraux | (Partial<NewAvisGeneraux> & AvisGenerauxFormDefaults)
  ): AvisGenerauxFormRawValue | PartialWithRequiredKeyOf<NewAvisGenerauxFormRawValue> {
    return {
      ...avisGeneraux,
      datePublication: avisGeneraux.datePublication ? avisGeneraux.datePublication.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
