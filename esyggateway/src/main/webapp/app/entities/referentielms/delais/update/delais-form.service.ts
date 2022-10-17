import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDelais, NewDelais } from '../delais.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDelais for edit and NewDelaisFormGroupInput for create.
 */
type DelaisFormGroupInput = IDelais | PartialWithRequiredKeyOf<NewDelais>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDelais | NewDelais> = Omit<T, 'debutValidite' | 'finValidite'> & {
  debutValidite?: string | null;
  finValidite?: string | null;
};

type DelaisFormRawValue = FormValueOf<IDelais>;

type NewDelaisFormRawValue = FormValueOf<NewDelais>;

type DelaisFormDefaults = Pick<NewDelais, 'id' | 'debutValidite' | 'finValidite'>;

type DelaisFormGroupContent = {
  id: FormControl<DelaisFormRawValue['id'] | NewDelais['id']>;
  libelle: FormControl<DelaisFormRawValue['libelle']>;
  code: FormControl<DelaisFormRawValue['code']>;
  unite: FormControl<DelaisFormRawValue['unite']>;
  valeur: FormControl<DelaisFormRawValue['valeur']>;
  debutValidite: FormControl<DelaisFormRawValue['debutValidite']>;
  finValidite: FormControl<DelaisFormRawValue['finValidite']>;
  commentaires: FormControl<DelaisFormRawValue['commentaires']>;
};

export type DelaisFormGroup = FormGroup<DelaisFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DelaisFormService {
  createDelaisFormGroup(delais: DelaisFormGroupInput = { id: null }): DelaisFormGroup {
    const delaisRawValue = this.convertDelaisToDelaisRawValue({
      ...this.getFormDefaults(),
      ...delais,
    });
    return new FormGroup<DelaisFormGroupContent>({
      id: new FormControl(
        { value: delaisRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      libelle: new FormControl(delaisRawValue.libelle, {
        validators: [Validators.required],
      }),
      code: new FormControl(delaisRawValue.code, {
        validators: [Validators.required],
      }),
      unite: new FormControl(delaisRawValue.unite, {
        validators: [Validators.required],
      }),
      valeur: new FormControl(delaisRawValue.valeur, {
        validators: [Validators.required],
      }),
      debutValidite: new FormControl(delaisRawValue.debutValidite, {
        validators: [Validators.required],
      }),
      finValidite: new FormControl(delaisRawValue.finValidite, {
        validators: [Validators.required],
      }),
      commentaires: new FormControl(delaisRawValue.commentaires),
    });
  }

  getDelais(form: DelaisFormGroup): IDelais | NewDelais {
    return this.convertDelaisRawValueToDelais(form.getRawValue() as DelaisFormRawValue | NewDelaisFormRawValue);
  }

  resetForm(form: DelaisFormGroup, delais: DelaisFormGroupInput): void {
    const delaisRawValue = this.convertDelaisToDelaisRawValue({ ...this.getFormDefaults(), ...delais });
    form.reset(
      {
        ...delaisRawValue,
        id: { value: delaisRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DelaisFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      debutValidite: currentTime,
      finValidite: currentTime,
    };
  }

  private convertDelaisRawValueToDelais(rawDelais: DelaisFormRawValue | NewDelaisFormRawValue): IDelais | NewDelais {
    return {
      ...rawDelais,
      debutValidite: dayjs(rawDelais.debutValidite, DATE_TIME_FORMAT),
      finValidite: dayjs(rawDelais.finValidite, DATE_TIME_FORMAT),
    };
  }

  private convertDelaisToDelaisRawValue(
    delais: IDelais | (Partial<NewDelais> & DelaisFormDefaults)
  ): DelaisFormRawValue | PartialWithRequiredKeyOf<NewDelaisFormRawValue> {
    return {
      ...delais,
      debutValidite: delais.debutValidite ? delais.debutValidite.format(DATE_TIME_FORMAT) : undefined,
      finValidite: delais.finValidite ? delais.finValidite.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
