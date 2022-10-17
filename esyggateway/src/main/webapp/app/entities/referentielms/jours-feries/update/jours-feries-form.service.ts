import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IJoursFeries, NewJoursFeries } from '../jours-feries.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IJoursFeries for edit and NewJoursFeriesFormGroupInput for create.
 */
type JoursFeriesFormGroupInput = IJoursFeries | PartialWithRequiredKeyOf<NewJoursFeries>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IJoursFeries | NewJoursFeries> = Omit<T, 'date'> & {
  date?: string | null;
};

type JoursFeriesFormRawValue = FormValueOf<IJoursFeries>;

type NewJoursFeriesFormRawValue = FormValueOf<NewJoursFeries>;

type JoursFeriesFormDefaults = Pick<NewJoursFeries, 'id' | 'date'>;

type JoursFeriesFormGroupContent = {
  id: FormControl<JoursFeriesFormRawValue['id'] | NewJoursFeries['id']>;
  date: FormControl<JoursFeriesFormRawValue['date']>;
  description: FormControl<JoursFeriesFormRawValue['description']>;
};

export type JoursFeriesFormGroup = FormGroup<JoursFeriesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class JoursFeriesFormService {
  createJoursFeriesFormGroup(joursFeries: JoursFeriesFormGroupInput = { id: null }): JoursFeriesFormGroup {
    const joursFeriesRawValue = this.convertJoursFeriesToJoursFeriesRawValue({
      ...this.getFormDefaults(),
      ...joursFeries,
    });
    return new FormGroup<JoursFeriesFormGroupContent>({
      id: new FormControl(
        { value: joursFeriesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      date: new FormControl(joursFeriesRawValue.date, {
        validators: [Validators.required],
      }),
      description: new FormControl(joursFeriesRawValue.description),
    });
  }

  getJoursFeries(form: JoursFeriesFormGroup): IJoursFeries | NewJoursFeries {
    return this.convertJoursFeriesRawValueToJoursFeries(form.getRawValue() as JoursFeriesFormRawValue | NewJoursFeriesFormRawValue);
  }

  resetForm(form: JoursFeriesFormGroup, joursFeries: JoursFeriesFormGroupInput): void {
    const joursFeriesRawValue = this.convertJoursFeriesToJoursFeriesRawValue({ ...this.getFormDefaults(), ...joursFeries });
    form.reset(
      {
        ...joursFeriesRawValue,
        id: { value: joursFeriesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): JoursFeriesFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      date: currentTime,
    };
  }

  private convertJoursFeriesRawValueToJoursFeries(
    rawJoursFeries: JoursFeriesFormRawValue | NewJoursFeriesFormRawValue
  ): IJoursFeries | NewJoursFeries {
    return {
      ...rawJoursFeries,
      date: dayjs(rawJoursFeries.date, DATE_TIME_FORMAT),
    };
  }

  private convertJoursFeriesToJoursFeriesRawValue(
    joursFeries: IJoursFeries | (Partial<NewJoursFeries> & JoursFeriesFormDefaults)
  ): JoursFeriesFormRawValue | PartialWithRequiredKeyOf<NewJoursFeriesFormRawValue> {
    return {
      ...joursFeries,
      date: joursFeries.date ? joursFeries.date.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
