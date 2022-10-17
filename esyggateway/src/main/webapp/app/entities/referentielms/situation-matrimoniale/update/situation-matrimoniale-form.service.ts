import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISituationMatrimoniale, NewSituationMatrimoniale } from '../situation-matrimoniale.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISituationMatrimoniale for edit and NewSituationMatrimonialeFormGroupInput for create.
 */
type SituationMatrimonialeFormGroupInput = ISituationMatrimoniale | PartialWithRequiredKeyOf<NewSituationMatrimoniale>;

type SituationMatrimonialeFormDefaults = Pick<NewSituationMatrimoniale, 'id'>;

type SituationMatrimonialeFormGroupContent = {
  id: FormControl<ISituationMatrimoniale['id'] | NewSituationMatrimoniale['id']>;
  libelle: FormControl<ISituationMatrimoniale['libelle']>;
};

export type SituationMatrimonialeFormGroup = FormGroup<SituationMatrimonialeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SituationMatrimonialeFormService {
  createSituationMatrimonialeFormGroup(
    situationMatrimoniale: SituationMatrimonialeFormGroupInput = { id: null }
  ): SituationMatrimonialeFormGroup {
    const situationMatrimonialeRawValue = {
      ...this.getFormDefaults(),
      ...situationMatrimoniale,
    };
    return new FormGroup<SituationMatrimonialeFormGroupContent>({
      id: new FormControl(
        { value: situationMatrimonialeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      libelle: new FormControl(situationMatrimonialeRawValue.libelle, {
        validators: [Validators.required],
      }),
    });
  }

  getSituationMatrimoniale(form: SituationMatrimonialeFormGroup): ISituationMatrimoniale | NewSituationMatrimoniale {
    return form.getRawValue() as ISituationMatrimoniale | NewSituationMatrimoniale;
  }

  resetForm(form: SituationMatrimonialeFormGroup, situationMatrimoniale: SituationMatrimonialeFormGroupInput): void {
    const situationMatrimonialeRawValue = { ...this.getFormDefaults(), ...situationMatrimoniale };
    form.reset(
      {
        ...situationMatrimonialeRawValue,
        id: { value: situationMatrimonialeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SituationMatrimonialeFormDefaults {
    return {
      id: null,
    };
  }
}
