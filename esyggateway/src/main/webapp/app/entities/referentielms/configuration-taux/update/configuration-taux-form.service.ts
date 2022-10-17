import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IConfigurationTaux, NewConfigurationTaux } from '../configuration-taux.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IConfigurationTaux for edit and NewConfigurationTauxFormGroupInput for create.
 */
type ConfigurationTauxFormGroupInput = IConfigurationTaux | PartialWithRequiredKeyOf<NewConfigurationTaux>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IConfigurationTaux | NewConfigurationTaux> = Omit<T, 'dateDebut' | 'dateFin'> & {
  dateDebut?: string | null;
  dateFin?: string | null;
};

type ConfigurationTauxFormRawValue = FormValueOf<IConfigurationTaux>;

type NewConfigurationTauxFormRawValue = FormValueOf<NewConfigurationTaux>;

type ConfigurationTauxFormDefaults = Pick<NewConfigurationTaux, 'id' | 'dateDebut' | 'dateFin' | 'invalid'>;

type ConfigurationTauxFormGroupContent = {
  id: FormControl<ConfigurationTauxFormRawValue['id'] | NewConfigurationTaux['id']>;
  code: FormControl<ConfigurationTauxFormRawValue['code']>;
  libelle: FormControl<ConfigurationTauxFormRawValue['libelle']>;
  taux: FormControl<ConfigurationTauxFormRawValue['taux']>;
  dateDebut: FormControl<ConfigurationTauxFormRawValue['dateDebut']>;
  dateFin: FormControl<ConfigurationTauxFormRawValue['dateFin']>;
  invalid: FormControl<ConfigurationTauxFormRawValue['invalid']>;
  pays: FormControl<ConfigurationTauxFormRawValue['pays']>;
};

export type ConfigurationTauxFormGroup = FormGroup<ConfigurationTauxFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ConfigurationTauxFormService {
  createConfigurationTauxFormGroup(configurationTaux: ConfigurationTauxFormGroupInput = { id: null }): ConfigurationTauxFormGroup {
    const configurationTauxRawValue = this.convertConfigurationTauxToConfigurationTauxRawValue({
      ...this.getFormDefaults(),
      ...configurationTaux,
    });
    return new FormGroup<ConfigurationTauxFormGroupContent>({
      id: new FormControl(
        { value: configurationTauxRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      code: new FormControl(configurationTauxRawValue.code),
      libelle: new FormControl(configurationTauxRawValue.libelle),
      taux: new FormControl(configurationTauxRawValue.taux),
      dateDebut: new FormControl(configurationTauxRawValue.dateDebut, {
        validators: [Validators.required],
      }),
      dateFin: new FormControl(configurationTauxRawValue.dateFin, {
        validators: [Validators.required],
      }),
      invalid: new FormControl(configurationTauxRawValue.invalid, {
        validators: [Validators.required],
      }),
      pays: new FormControl(configurationTauxRawValue.pays),
    });
  }

  getConfigurationTaux(form: ConfigurationTauxFormGroup): IConfigurationTaux | NewConfigurationTaux {
    return this.convertConfigurationTauxRawValueToConfigurationTaux(
      form.getRawValue() as ConfigurationTauxFormRawValue | NewConfigurationTauxFormRawValue
    );
  }

  resetForm(form: ConfigurationTauxFormGroup, configurationTaux: ConfigurationTauxFormGroupInput): void {
    const configurationTauxRawValue = this.convertConfigurationTauxToConfigurationTauxRawValue({
      ...this.getFormDefaults(),
      ...configurationTaux,
    });
    form.reset(
      {
        ...configurationTauxRawValue,
        id: { value: configurationTauxRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ConfigurationTauxFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dateDebut: currentTime,
      dateFin: currentTime,
      invalid: false,
    };
  }

  private convertConfigurationTauxRawValueToConfigurationTaux(
    rawConfigurationTaux: ConfigurationTauxFormRawValue | NewConfigurationTauxFormRawValue
  ): IConfigurationTaux | NewConfigurationTaux {
    return {
      ...rawConfigurationTaux,
      dateDebut: dayjs(rawConfigurationTaux.dateDebut, DATE_TIME_FORMAT),
      dateFin: dayjs(rawConfigurationTaux.dateFin, DATE_TIME_FORMAT),
    };
  }

  private convertConfigurationTauxToConfigurationTauxRawValue(
    configurationTaux: IConfigurationTaux | (Partial<NewConfigurationTaux> & ConfigurationTauxFormDefaults)
  ): ConfigurationTauxFormRawValue | PartialWithRequiredKeyOf<NewConfigurationTauxFormRawValue> {
    return {
      ...configurationTaux,
      dateDebut: configurationTaux.dateDebut ? configurationTaux.dateDebut.format(DATE_TIME_FORMAT) : undefined,
      dateFin: configurationTaux.dateFin ? configurationTaux.dateFin.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
