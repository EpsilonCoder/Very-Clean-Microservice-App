import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISourcesFinancement, NewSourcesFinancement } from '../sources-financement.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISourcesFinancement for edit and NewSourcesFinancementFormGroupInput for create.
 */
type SourcesFinancementFormGroupInput = ISourcesFinancement | PartialWithRequiredKeyOf<NewSourcesFinancement>;

type SourcesFinancementFormDefaults = Pick<NewSourcesFinancement, 'id'>;

type SourcesFinancementFormGroupContent = {
  id: FormControl<ISourcesFinancement['id'] | NewSourcesFinancement['id']>;
  code: FormControl<ISourcesFinancement['code']>;
  libelle: FormControl<ISourcesFinancement['libelle']>;
  corbeille: FormControl<ISourcesFinancement['corbeille']>;
};

export type SourcesFinancementFormGroup = FormGroup<SourcesFinancementFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SourcesFinancementFormService {
  createSourcesFinancementFormGroup(sourcesFinancement: SourcesFinancementFormGroupInput = { id: null }): SourcesFinancementFormGroup {
    const sourcesFinancementRawValue = {
      ...this.getFormDefaults(),
      ...sourcesFinancement,
    };
    return new FormGroup<SourcesFinancementFormGroupContent>({
      id: new FormControl(
        { value: sourcesFinancementRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      code: new FormControl(sourcesFinancementRawValue.code, {
        validators: [Validators.required],
      }),
      libelle: new FormControl(sourcesFinancementRawValue.libelle, {
        validators: [Validators.required],
      }),
      corbeille: new FormControl(sourcesFinancementRawValue.corbeille),
    });
  }

  getSourcesFinancement(form: SourcesFinancementFormGroup): ISourcesFinancement | NewSourcesFinancement {
    return form.getRawValue() as ISourcesFinancement | NewSourcesFinancement;
  }

  resetForm(form: SourcesFinancementFormGroup, sourcesFinancement: SourcesFinancementFormGroupInput): void {
    const sourcesFinancementRawValue = { ...this.getFormDefaults(), ...sourcesFinancement };
    form.reset(
      {
        ...sourcesFinancementRawValue,
        id: { value: sourcesFinancementRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SourcesFinancementFormDefaults {
    return {
      id: null,
    };
  }
}
