import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPersonnesRessources, NewPersonnesRessources } from '../personnes-ressources.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPersonnesRessources for edit and NewPersonnesRessourcesFormGroupInput for create.
 */
type PersonnesRessourcesFormGroupInput = IPersonnesRessources | PartialWithRequiredKeyOf<NewPersonnesRessources>;

type PersonnesRessourcesFormDefaults = Pick<NewPersonnesRessources, 'id'>;

type PersonnesRessourcesFormGroupContent = {
  id: FormControl<IPersonnesRessources['id'] | NewPersonnesRessources['id']>;
  prenom: FormControl<IPersonnesRessources['prenom']>;
  nom: FormControl<IPersonnesRessources['nom']>;
  email: FormControl<IPersonnesRessources['email']>;
  telephone: FormControl<IPersonnesRessources['telephone']>;
  fonction: FormControl<IPersonnesRessources['fonction']>;
  commentaires: FormControl<IPersonnesRessources['commentaires']>;
};

export type PersonnesRessourcesFormGroup = FormGroup<PersonnesRessourcesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PersonnesRessourcesFormService {
  createPersonnesRessourcesFormGroup(personnesRessources: PersonnesRessourcesFormGroupInput = { id: null }): PersonnesRessourcesFormGroup {
    const personnesRessourcesRawValue = {
      ...this.getFormDefaults(),
      ...personnesRessources,
    };
    return new FormGroup<PersonnesRessourcesFormGroupContent>({
      id: new FormControl(
        { value: personnesRessourcesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      prenom: new FormControl(personnesRessourcesRawValue.prenom, {
        validators: [Validators.required],
      }),
      nom: new FormControl(personnesRessourcesRawValue.nom, {
        validators: [Validators.required],
      }),
      email: new FormControl(personnesRessourcesRawValue.email, {
        validators: [Validators.required],
      }),
      telephone: new FormControl(personnesRessourcesRawValue.telephone, {
        validators: [Validators.required],
      }),
      fonction: new FormControl(personnesRessourcesRawValue.fonction, {
        validators: [Validators.required],
      }),
      commentaires: new FormControl(personnesRessourcesRawValue.commentaires),
    });
  }

  getPersonnesRessources(form: PersonnesRessourcesFormGroup): IPersonnesRessources | NewPersonnesRessources {
    return form.getRawValue() as IPersonnesRessources | NewPersonnesRessources;
  }

  resetForm(form: PersonnesRessourcesFormGroup, personnesRessources: PersonnesRessourcesFormGroupInput): void {
    const personnesRessourcesRawValue = { ...this.getFormDefaults(), ...personnesRessources };
    form.reset(
      {
        ...personnesRessourcesRawValue,
        id: { value: personnesRessourcesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PersonnesRessourcesFormDefaults {
    return {
      id: null,
    };
  }
}
