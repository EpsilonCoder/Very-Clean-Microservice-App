import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPiecesAdministratives, NewPiecesAdministratives } from '../pieces-administratives.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPiecesAdministratives for edit and NewPiecesAdministrativesFormGroupInput for create.
 */
type PiecesAdministrativesFormGroupInput = IPiecesAdministratives | PartialWithRequiredKeyOf<NewPiecesAdministratives>;

type PiecesAdministrativesFormDefaults = Pick<NewPiecesAdministratives, 'id'>;

type PiecesAdministrativesFormGroupContent = {
  id: FormControl<IPiecesAdministratives['id'] | NewPiecesAdministratives['id']>;
  code: FormControl<IPiecesAdministratives['code']>;
  libelle: FormControl<IPiecesAdministratives['libelle']>;
  description: FormControl<IPiecesAdministratives['description']>;
  localisation: FormControl<IPiecesAdministratives['localisation']>;
  type: FormControl<IPiecesAdministratives['type']>;
};

export type PiecesAdministrativesFormGroup = FormGroup<PiecesAdministrativesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PiecesAdministrativesFormService {
  createPiecesAdministrativesFormGroup(
    piecesAdministratives: PiecesAdministrativesFormGroupInput = { id: null }
  ): PiecesAdministrativesFormGroup {
    const piecesAdministrativesRawValue = {
      ...this.getFormDefaults(),
      ...piecesAdministratives,
    };
    return new FormGroup<PiecesAdministrativesFormGroupContent>({
      id: new FormControl(
        { value: piecesAdministrativesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      code: new FormControl(piecesAdministrativesRawValue.code, {
        validators: [Validators.required],
      }),
      libelle: new FormControl(piecesAdministrativesRawValue.libelle, {
        validators: [Validators.required],
      }),
      description: new FormControl(piecesAdministrativesRawValue.description),
      localisation: new FormControl(piecesAdministrativesRawValue.localisation, {
        validators: [Validators.required],
      }),
      type: new FormControl(piecesAdministrativesRawValue.type),
    });
  }

  getPiecesAdministratives(form: PiecesAdministrativesFormGroup): IPiecesAdministratives | NewPiecesAdministratives {
    return form.getRawValue() as IPiecesAdministratives | NewPiecesAdministratives;
  }

  resetForm(form: PiecesAdministrativesFormGroup, piecesAdministratives: PiecesAdministrativesFormGroupInput): void {
    const piecesAdministrativesRawValue = { ...this.getFormDefaults(), ...piecesAdministratives };
    form.reset(
      {
        ...piecesAdministrativesRawValue,
        id: { value: piecesAdministrativesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PiecesAdministrativesFormDefaults {
    return {
      id: null,
    };
  }
}
