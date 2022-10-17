import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IModeSelection, NewModeSelection } from '../mode-selection.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IModeSelection for edit and NewModeSelectionFormGroupInput for create.
 */
type ModeSelectionFormGroupInput = IModeSelection | PartialWithRequiredKeyOf<NewModeSelection>;

type ModeSelectionFormDefaults = Pick<NewModeSelection, 'id'>;

type ModeSelectionFormGroupContent = {
  id: FormControl<IModeSelection['id'] | NewModeSelection['id']>;
  libelle: FormControl<IModeSelection['libelle']>;
  code: FormControl<IModeSelection['code']>;
  description: FormControl<IModeSelection['description']>;
};

export type ModeSelectionFormGroup = FormGroup<ModeSelectionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ModeSelectionFormService {
  createModeSelectionFormGroup(modeSelection: ModeSelectionFormGroupInput = { id: null }): ModeSelectionFormGroup {
    const modeSelectionRawValue = {
      ...this.getFormDefaults(),
      ...modeSelection,
    };
    return new FormGroup<ModeSelectionFormGroupContent>({
      id: new FormControl(
        { value: modeSelectionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      libelle: new FormControl(modeSelectionRawValue.libelle, {
        validators: [Validators.required, Validators.maxLength(255)],
      }),
      code: new FormControl(modeSelectionRawValue.code, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      description: new FormControl(modeSelectionRawValue.description),
    });
  }

  getModeSelection(form: ModeSelectionFormGroup): IModeSelection | NewModeSelection {
    return form.getRawValue() as IModeSelection | NewModeSelection;
  }

  resetForm(form: ModeSelectionFormGroup, modeSelection: ModeSelectionFormGroupInput): void {
    const modeSelectionRawValue = { ...this.getFormDefaults(), ...modeSelection };
    form.reset(
      {
        ...modeSelectionRawValue,
        id: { value: modeSelectionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ModeSelectionFormDefaults {
    return {
      id: null,
    };
  }
}
