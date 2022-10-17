import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISpecialitesPersonnel, NewSpecialitesPersonnel } from '../specialites-personnel.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISpecialitesPersonnel for edit and NewSpecialitesPersonnelFormGroupInput for create.
 */
type SpecialitesPersonnelFormGroupInput = ISpecialitesPersonnel | PartialWithRequiredKeyOf<NewSpecialitesPersonnel>;

type SpecialitesPersonnelFormDefaults = Pick<NewSpecialitesPersonnel, 'id'>;

type SpecialitesPersonnelFormGroupContent = {
  id: FormControl<ISpecialitesPersonnel['id'] | NewSpecialitesPersonnel['id']>;
  libelle: FormControl<ISpecialitesPersonnel['libelle']>;
};

export type SpecialitesPersonnelFormGroup = FormGroup<SpecialitesPersonnelFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SpecialitesPersonnelFormService {
  createSpecialitesPersonnelFormGroup(
    specialitesPersonnel: SpecialitesPersonnelFormGroupInput = { id: null }
  ): SpecialitesPersonnelFormGroup {
    const specialitesPersonnelRawValue = {
      ...this.getFormDefaults(),
      ...specialitesPersonnel,
    };
    return new FormGroup<SpecialitesPersonnelFormGroupContent>({
      id: new FormControl(
        { value: specialitesPersonnelRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      libelle: new FormControl(specialitesPersonnelRawValue.libelle, {
        validators: [Validators.required],
      }),
    });
  }

  getSpecialitesPersonnel(form: SpecialitesPersonnelFormGroup): ISpecialitesPersonnel | NewSpecialitesPersonnel {
    return form.getRawValue() as ISpecialitesPersonnel | NewSpecialitesPersonnel;
  }

  resetForm(form: SpecialitesPersonnelFormGroup, specialitesPersonnel: SpecialitesPersonnelFormGroupInput): void {
    const specialitesPersonnelRawValue = { ...this.getFormDefaults(), ...specialitesPersonnel };
    form.reset(
      {
        ...specialitesPersonnelRawValue,
        id: { value: specialitesPersonnelRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SpecialitesPersonnelFormDefaults {
    return {
      id: null,
    };
  }
}
