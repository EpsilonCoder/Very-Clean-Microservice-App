import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IGroupesImputation, NewGroupesImputation } from '../groupes-imputation.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IGroupesImputation for edit and NewGroupesImputationFormGroupInput for create.
 */
type GroupesImputationFormGroupInput = IGroupesImputation | PartialWithRequiredKeyOf<NewGroupesImputation>;

type GroupesImputationFormDefaults = Pick<NewGroupesImputation, 'id'>;

type GroupesImputationFormGroupContent = {
  id: FormControl<IGroupesImputation['id'] | NewGroupesImputation['id']>;
  libelle: FormControl<IGroupesImputation['libelle']>;
  description: FormControl<IGroupesImputation['description']>;
};

export type GroupesImputationFormGroup = FormGroup<GroupesImputationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GroupesImputationFormService {
  createGroupesImputationFormGroup(groupesImputation: GroupesImputationFormGroupInput = { id: null }): GroupesImputationFormGroup {
    const groupesImputationRawValue = {
      ...this.getFormDefaults(),
      ...groupesImputation,
    };
    return new FormGroup<GroupesImputationFormGroupContent>({
      id: new FormControl(
        { value: groupesImputationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      libelle: new FormControl(groupesImputationRawValue.libelle, {
        validators: [Validators.required],
      }),
      description: new FormControl(groupesImputationRawValue.description),
    });
  }

  getGroupesImputation(form: GroupesImputationFormGroup): IGroupesImputation | NewGroupesImputation {
    return form.getRawValue() as IGroupesImputation | NewGroupesImputation;
  }

  resetForm(form: GroupesImputationFormGroup, groupesImputation: GroupesImputationFormGroupInput): void {
    const groupesImputationRawValue = { ...this.getFormDefaults(), ...groupesImputation };
    form.reset(
      {
        ...groupesImputationRawValue,
        id: { value: groupesImputationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): GroupesImputationFormDefaults {
    return {
      id: null,
    };
  }
}
