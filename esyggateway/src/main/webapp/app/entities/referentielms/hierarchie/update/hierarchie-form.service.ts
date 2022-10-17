import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IHierarchie, NewHierarchie } from '../hierarchie.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IHierarchie for edit and NewHierarchieFormGroupInput for create.
 */
type HierarchieFormGroupInput = IHierarchie | PartialWithRequiredKeyOf<NewHierarchie>;

type HierarchieFormDefaults = Pick<NewHierarchie, 'id'>;

type HierarchieFormGroupContent = {
  id: FormControl<IHierarchie['id'] | NewHierarchie['id']>;
  libelle: FormControl<IHierarchie['libelle']>;
};

export type HierarchieFormGroup = FormGroup<HierarchieFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class HierarchieFormService {
  createHierarchieFormGroup(hierarchie: HierarchieFormGroupInput = { id: null }): HierarchieFormGroup {
    const hierarchieRawValue = {
      ...this.getFormDefaults(),
      ...hierarchie,
    };
    return new FormGroup<HierarchieFormGroupContent>({
      id: new FormControl(
        { value: hierarchieRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      libelle: new FormControl(hierarchieRawValue.libelle, {
        validators: [Validators.required],
      }),
    });
  }

  getHierarchie(form: HierarchieFormGroup): IHierarchie | NewHierarchie {
    return form.getRawValue() as IHierarchie | NewHierarchie;
  }

  resetForm(form: HierarchieFormGroup, hierarchie: HierarchieFormGroupInput): void {
    const hierarchieRawValue = { ...this.getFormDefaults(), ...hierarchie };
    form.reset(
      {
        ...hierarchieRawValue,
        id: { value: hierarchieRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): HierarchieFormDefaults {
    return {
      id: null,
    };
  }
}
