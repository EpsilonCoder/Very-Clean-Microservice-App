import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISygAutoriteContractante, NewSygAutoriteContractante } from '../syg-autorite-contractante.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISygAutoriteContractante for edit and NewSygAutoriteContractanteFormGroupInput for create.
 */
type SygAutoriteContractanteFormGroupInput = ISygAutoriteContractante | PartialWithRequiredKeyOf<NewSygAutoriteContractante>;

type SygAutoriteContractanteFormDefaults = Pick<NewSygAutoriteContractante, 'id'>;

type SygAutoriteContractanteFormGroupContent = {
  id: FormControl<ISygAutoriteContractante['id'] | NewSygAutoriteContractante['id']>;
  ordre: FormControl<ISygAutoriteContractante['ordre']>;
  denomination: FormControl<ISygAutoriteContractante['denomination']>;
  responsable: FormControl<ISygAutoriteContractante['responsable']>;
  adresse: FormControl<ISygAutoriteContractante['adresse']>;
  telephone: FormControl<ISygAutoriteContractante['telephone']>;
  fax: FormControl<ISygAutoriteContractante['fax']>;
  email: FormControl<ISygAutoriteContractante['email']>;
  sigle: FormControl<ISygAutoriteContractante['sigle']>;
  urlsiteweb: FormControl<ISygAutoriteContractante['urlsiteweb']>;
  approbation: FormControl<ISygAutoriteContractante['approbation']>;
  logo: FormControl<ISygAutoriteContractante['logo']>;
  logoContentType: FormControl<ISygAutoriteContractante['logoContentType']>;
  typeAutoriteContractante: FormControl<ISygAutoriteContractante['typeAutoriteContractante']>;
};

export type SygAutoriteContractanteFormGroup = FormGroup<SygAutoriteContractanteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SygAutoriteContractanteFormService {
  createSygAutoriteContractanteFormGroup(
    sygAutoriteContractante: SygAutoriteContractanteFormGroupInput = { id: null }
  ): SygAutoriteContractanteFormGroup {
    const sygAutoriteContractanteRawValue = {
      ...this.getFormDefaults(),
      ...sygAutoriteContractante,
    };
    return new FormGroup<SygAutoriteContractanteFormGroupContent>({
      id: new FormControl(
        { value: sygAutoriteContractanteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      ordre: new FormControl(sygAutoriteContractanteRawValue.ordre, {
        validators: [Validators.required],
      }),
      denomination: new FormControl(sygAutoriteContractanteRawValue.denomination, {
        validators: [Validators.required],
      }),
      responsable: new FormControl(sygAutoriteContractanteRawValue.responsable, {
        validators: [Validators.required],
      }),
      adresse: new FormControl(sygAutoriteContractanteRawValue.adresse, {
        validators: [Validators.required],
      }),
      telephone: new FormControl(sygAutoriteContractanteRawValue.telephone, {
        validators: [Validators.required],
      }),
      fax: new FormControl(sygAutoriteContractanteRawValue.fax),
      email: new FormControl(sygAutoriteContractanteRawValue.email, {
        validators: [Validators.required],
      }),
      sigle: new FormControl(sygAutoriteContractanteRawValue.sigle, {
        validators: [Validators.required],
      }),
      urlsiteweb: new FormControl(sygAutoriteContractanteRawValue.urlsiteweb),
      approbation: new FormControl(sygAutoriteContractanteRawValue.approbation),
      logo: new FormControl(sygAutoriteContractanteRawValue.logo),
      logoContentType: new FormControl(sygAutoriteContractanteRawValue.logoContentType),
      typeAutoriteContractante: new FormControl(sygAutoriteContractanteRawValue.typeAutoriteContractante, {
        validators: [Validators.required],
      }),
    });
  }

  getSygAutoriteContractante(form: SygAutoriteContractanteFormGroup): ISygAutoriteContractante | NewSygAutoriteContractante {
    return form.getRawValue() as ISygAutoriteContractante | NewSygAutoriteContractante;
  }

  resetForm(form: SygAutoriteContractanteFormGroup, sygAutoriteContractante: SygAutoriteContractanteFormGroupInput): void {
    const sygAutoriteContractanteRawValue = { ...this.getFormDefaults(), ...sygAutoriteContractante };
    form.reset(
      {
        ...sygAutoriteContractanteRawValue,
        id: { value: sygAutoriteContractanteRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SygAutoriteContractanteFormDefaults {
    return {
      id: null,
    };
  }
}
