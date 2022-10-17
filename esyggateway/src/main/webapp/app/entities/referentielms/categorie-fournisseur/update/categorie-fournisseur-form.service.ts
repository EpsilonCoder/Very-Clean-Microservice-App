import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICategorieFournisseur, NewCategorieFournisseur } from '../categorie-fournisseur.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICategorieFournisseur for edit and NewCategorieFournisseurFormGroupInput for create.
 */
type CategorieFournisseurFormGroupInput = ICategorieFournisseur | PartialWithRequiredKeyOf<NewCategorieFournisseur>;

type CategorieFournisseurFormDefaults = Pick<NewCategorieFournisseur, 'id'>;

type CategorieFournisseurFormGroupContent = {
  id: FormControl<ICategorieFournisseur['id'] | NewCategorieFournisseur['id']>;
  libelle: FormControl<ICategorieFournisseur['libelle']>;
  description: FormControl<ICategorieFournisseur['description']>;
};

export type CategorieFournisseurFormGroup = FormGroup<CategorieFournisseurFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CategorieFournisseurFormService {
  createCategorieFournisseurFormGroup(
    categorieFournisseur: CategorieFournisseurFormGroupInput = { id: null }
  ): CategorieFournisseurFormGroup {
    const categorieFournisseurRawValue = {
      ...this.getFormDefaults(),
      ...categorieFournisseur,
    };
    return new FormGroup<CategorieFournisseurFormGroupContent>({
      id: new FormControl(
        { value: categorieFournisseurRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      libelle: new FormControl(categorieFournisseurRawValue.libelle, {
        validators: [Validators.required],
      }),
      description: new FormControl(categorieFournisseurRawValue.description, {
        validators: [Validators.required],
      }),
    });
  }

  getCategorieFournisseur(form: CategorieFournisseurFormGroup): ICategorieFournisseur | NewCategorieFournisseur {
    return form.getRawValue() as ICategorieFournisseur | NewCategorieFournisseur;
  }

  resetForm(form: CategorieFournisseurFormGroup, categorieFournisseur: CategorieFournisseurFormGroupInput): void {
    const categorieFournisseurRawValue = { ...this.getFormDefaults(), ...categorieFournisseur };
    form.reset(
      {
        ...categorieFournisseurRawValue,
        id: { value: categorieFournisseurRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CategorieFournisseurFormDefaults {
    return {
      id: null,
    };
  }
}
