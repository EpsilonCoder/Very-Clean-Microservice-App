import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { INaturesGarantie, NewNaturesGarantie } from '../natures-garantie.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INaturesGarantie for edit and NewNaturesGarantieFormGroupInput for create.
 */
type NaturesGarantieFormGroupInput = INaturesGarantie | PartialWithRequiredKeyOf<NewNaturesGarantie>;

type NaturesGarantieFormDefaults = Pick<NewNaturesGarantie, 'id'>;

type NaturesGarantieFormGroupContent = {
  id: FormControl<INaturesGarantie['id'] | NewNaturesGarantie['id']>;
  libelle: FormControl<INaturesGarantie['libelle']>;
};

export type NaturesGarantieFormGroup = FormGroup<NaturesGarantieFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NaturesGarantieFormService {
  createNaturesGarantieFormGroup(naturesGarantie: NaturesGarantieFormGroupInput = { id: null }): NaturesGarantieFormGroup {
    const naturesGarantieRawValue = {
      ...this.getFormDefaults(),
      ...naturesGarantie,
    };
    return new FormGroup<NaturesGarantieFormGroupContent>({
      id: new FormControl(
        { value: naturesGarantieRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      libelle: new FormControl(naturesGarantieRawValue.libelle, {
        validators: [Validators.required],
      }),
    });
  }

  getNaturesGarantie(form: NaturesGarantieFormGroup): INaturesGarantie | NewNaturesGarantie {
    return form.getRawValue() as INaturesGarantie | NewNaturesGarantie;
  }

  resetForm(form: NaturesGarantieFormGroup, naturesGarantie: NaturesGarantieFormGroupInput): void {
    const naturesGarantieRawValue = { ...this.getFormDefaults(), ...naturesGarantie };
    form.reset(
      {
        ...naturesGarantieRawValue,
        id: { value: naturesGarantieRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): NaturesGarantieFormDefaults {
    return {
      id: null,
    };
  }
}
