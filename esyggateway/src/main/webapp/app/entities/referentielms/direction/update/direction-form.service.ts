import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDirection, NewDirection } from '../direction.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDirection for edit and NewDirectionFormGroupInput for create.
 */
type DirectionFormGroupInput = IDirection | PartialWithRequiredKeyOf<NewDirection>;

type DirectionFormDefaults = Pick<NewDirection, 'id'>;

type DirectionFormGroupContent = {
  id: FormControl<IDirection['id'] | NewDirection['id']>;
  sigle: FormControl<IDirection['sigle']>;
  libelle: FormControl<IDirection['libelle']>;
  description: FormControl<IDirection['description']>;
};

export type DirectionFormGroup = FormGroup<DirectionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DirectionFormService {
  createDirectionFormGroup(direction: DirectionFormGroupInput = { id: null }): DirectionFormGroup {
    const directionRawValue = {
      ...this.getFormDefaults(),
      ...direction,
    };
    return new FormGroup<DirectionFormGroupContent>({
      id: new FormControl(
        { value: directionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      sigle: new FormControl(directionRawValue.sigle, {
        validators: [Validators.required],
      }),
      libelle: new FormControl(directionRawValue.libelle, {
        validators: [Validators.required],
      }),
      description: new FormControl(directionRawValue.description),
    });
  }

  getDirection(form: DirectionFormGroup): IDirection | NewDirection {
    return form.getRawValue() as IDirection | NewDirection;
  }

  resetForm(form: DirectionFormGroup, direction: DirectionFormGroupInput): void {
    const directionRawValue = { ...this.getFormDefaults(), ...direction };
    form.reset(
      {
        ...directionRawValue,
        id: { value: directionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DirectionFormDefaults {
    return {
      id: null,
    };
  }
}
