import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICriteresQualification, NewCriteresQualification } from '../criteres-qualification.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICriteresQualification for edit and NewCriteresQualificationFormGroupInput for create.
 */
type CriteresQualificationFormGroupInput = ICriteresQualification | PartialWithRequiredKeyOf<NewCriteresQualification>;

type CriteresQualificationFormDefaults = Pick<NewCriteresQualification, 'id'>;

type CriteresQualificationFormGroupContent = {
  id: FormControl<ICriteresQualification['id'] | NewCriteresQualification['id']>;
  libelle: FormControl<ICriteresQualification['libelle']>;
};

export type CriteresQualificationFormGroup = FormGroup<CriteresQualificationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CriteresQualificationFormService {
  createCriteresQualificationFormGroup(
    criteresQualification: CriteresQualificationFormGroupInput = { id: null }
  ): CriteresQualificationFormGroup {
    const criteresQualificationRawValue = {
      ...this.getFormDefaults(),
      ...criteresQualification,
    };
    return new FormGroup<CriteresQualificationFormGroupContent>({
      id: new FormControl(
        { value: criteresQualificationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      libelle: new FormControl(criteresQualificationRawValue.libelle),
    });
  }

  getCriteresQualification(form: CriteresQualificationFormGroup): ICriteresQualification | NewCriteresQualification {
    return form.getRawValue() as ICriteresQualification | NewCriteresQualification;
  }

  resetForm(form: CriteresQualificationFormGroup, criteresQualification: CriteresQualificationFormGroupInput): void {
    const criteresQualificationRawValue = { ...this.getFormDefaults(), ...criteresQualification };
    form.reset(
      {
        ...criteresQualificationRawValue,
        id: { value: criteresQualificationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CriteresQualificationFormDefaults {
    return {
      id: null,
    };
  }
}
