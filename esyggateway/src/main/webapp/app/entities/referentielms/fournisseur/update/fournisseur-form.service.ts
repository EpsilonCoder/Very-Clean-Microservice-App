import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IFournisseur, NewFournisseur } from '../fournisseur.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFournisseur for edit and NewFournisseurFormGroupInput for create.
 */
type FournisseurFormGroupInput = IFournisseur | PartialWithRequiredKeyOf<NewFournisseur>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IFournisseur | NewFournisseur> = Omit<T, 'date'> & {
  date?: string | null;
};

type FournisseurFormRawValue = FormValueOf<IFournisseur>;

type NewFournisseurFormRawValue = FormValueOf<NewFournisseur>;

type FournisseurFormDefaults = Pick<NewFournisseur, 'id' | 'date'>;

type FournisseurFormGroupContent = {
  id: FormControl<FournisseurFormRawValue['id'] | NewFournisseur['id']>;
  raisonSociale: FormControl<FournisseurFormRawValue['raisonSociale']>;
  adresse: FormControl<FournisseurFormRawValue['adresse']>;
  email: FormControl<FournisseurFormRawValue['email']>;
  telephone: FormControl<FournisseurFormRawValue['telephone']>;
  pieceJointe: FormControl<FournisseurFormRawValue['pieceJointe']>;
  numeroRegistreCommerce: FormControl<FournisseurFormRawValue['numeroRegistreCommerce']>;
  date: FormControl<FournisseurFormRawValue['date']>;
  sigle: FormControl<FournisseurFormRawValue['sigle']>;
  numeroIdentiteFiscale: FormControl<FournisseurFormRawValue['numeroIdentiteFiscale']>;
  categorieFournisseur: FormControl<FournisseurFormRawValue['categorieFournisseur']>;
  pays: FormControl<FournisseurFormRawValue['pays']>;
};

export type FournisseurFormGroup = FormGroup<FournisseurFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FournisseurFormService {
  createFournisseurFormGroup(fournisseur: FournisseurFormGroupInput = { id: null }): FournisseurFormGroup {
    const fournisseurRawValue = this.convertFournisseurToFournisseurRawValue({
      ...this.getFormDefaults(),
      ...fournisseur,
    });
    return new FormGroup<FournisseurFormGroupContent>({
      id: new FormControl(
        { value: fournisseurRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      raisonSociale: new FormControl(fournisseurRawValue.raisonSociale, {
        validators: [Validators.required],
      }),
      adresse: new FormControl(fournisseurRawValue.adresse, {
        validators: [Validators.required],
      }),
      email: new FormControl(fournisseurRawValue.email, {
        validators: [Validators.required],
      }),
      telephone: new FormControl(fournisseurRawValue.telephone, {
        validators: [Validators.required],
      }),
      pieceJointe: new FormControl(fournisseurRawValue.pieceJointe),
      numeroRegistreCommerce: new FormControl(fournisseurRawValue.numeroRegistreCommerce),
      date: new FormControl(fournisseurRawValue.date, {
        validators: [Validators.required],
      }),
      sigle: new FormControl(fournisseurRawValue.sigle),
      numeroIdentiteFiscale: new FormControl(fournisseurRawValue.numeroIdentiteFiscale),
      categorieFournisseur: new FormControl(fournisseurRawValue.categorieFournisseur),
      pays: new FormControl(fournisseurRawValue.pays),
    });
  }

  getFournisseur(form: FournisseurFormGroup): IFournisseur | NewFournisseur {
    return this.convertFournisseurRawValueToFournisseur(form.getRawValue() as FournisseurFormRawValue | NewFournisseurFormRawValue);
  }

  resetForm(form: FournisseurFormGroup, fournisseur: FournisseurFormGroupInput): void {
    const fournisseurRawValue = this.convertFournisseurToFournisseurRawValue({ ...this.getFormDefaults(), ...fournisseur });
    form.reset(
      {
        ...fournisseurRawValue,
        id: { value: fournisseurRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FournisseurFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      date: currentTime,
    };
  }

  private convertFournisseurRawValueToFournisseur(
    rawFournisseur: FournisseurFormRawValue | NewFournisseurFormRawValue
  ): IFournisseur | NewFournisseur {
    return {
      ...rawFournisseur,
      date: dayjs(rawFournisseur.date, DATE_TIME_FORMAT),
    };
  }

  private convertFournisseurToFournisseurRawValue(
    fournisseur: IFournisseur | (Partial<NewFournisseur> & FournisseurFormDefaults)
  ): FournisseurFormRawValue | PartialWithRequiredKeyOf<NewFournisseurFormRawValue> {
    return {
      ...fournisseur,
      date: fournisseur.date ? fournisseur.date.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
