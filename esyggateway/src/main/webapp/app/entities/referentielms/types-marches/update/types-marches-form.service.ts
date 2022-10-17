import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITypesMarches, NewTypesMarches } from '../types-marches.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITypesMarches for edit and NewTypesMarchesFormGroupInput for create.
 */
type TypesMarchesFormGroupInput = ITypesMarches | PartialWithRequiredKeyOf<NewTypesMarches>;

type TypesMarchesFormDefaults = Pick<NewTypesMarches, 'id'>;

type TypesMarchesFormGroupContent = {
  id: FormControl<ITypesMarches['id'] | NewTypesMarches['id']>;
  code: FormControl<ITypesMarches['code']>;
  libelle: FormControl<ITypesMarches['libelle']>;
  description: FormControl<ITypesMarches['description']>;
};

export type TypesMarchesFormGroup = FormGroup<TypesMarchesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TypesMarchesFormService {
  createTypesMarchesFormGroup(typesMarches: TypesMarchesFormGroupInput = { id: null }): TypesMarchesFormGroup {
    const typesMarchesRawValue = {
      ...this.getFormDefaults(),
      ...typesMarches,
    };
    return new FormGroup<TypesMarchesFormGroupContent>({
      id: new FormControl(
        { value: typesMarchesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      code: new FormControl(typesMarchesRawValue.code, {
        validators: [Validators.required],
      }),
      libelle: new FormControl(typesMarchesRawValue.libelle, {
        validators: [Validators.required],
      }),
      description: new FormControl(typesMarchesRawValue.description, {
        validators: [Validators.required],
      }),
    });
  }

  getTypesMarches(form: TypesMarchesFormGroup): ITypesMarches | NewTypesMarches {
    return form.getRawValue() as ITypesMarches | NewTypesMarches;
  }

  resetForm(form: TypesMarchesFormGroup, typesMarches: TypesMarchesFormGroupInput): void {
    const typesMarchesRawValue = { ...this.getFormDefaults(), ...typesMarches };
    form.reset(
      {
        ...typesMarchesRawValue,
        id: { value: typesMarchesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TypesMarchesFormDefaults {
    return {
      id: null,
    };
  }
}
