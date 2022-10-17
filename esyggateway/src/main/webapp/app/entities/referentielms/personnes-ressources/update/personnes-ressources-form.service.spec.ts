import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../personnes-ressources.test-samples';

import { PersonnesRessourcesFormService } from './personnes-ressources-form.service';

describe('PersonnesRessources Form Service', () => {
  let service: PersonnesRessourcesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PersonnesRessourcesFormService);
  });

  describe('Service methods', () => {
    describe('createPersonnesRessourcesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPersonnesRessourcesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            prenom: expect.any(Object),
            nom: expect.any(Object),
            email: expect.any(Object),
            telephone: expect.any(Object),
            fonction: expect.any(Object),
            commentaires: expect.any(Object),
          })
        );
      });

      it('passing IPersonnesRessources should create a new form with FormGroup', () => {
        const formGroup = service.createPersonnesRessourcesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            prenom: expect.any(Object),
            nom: expect.any(Object),
            email: expect.any(Object),
            telephone: expect.any(Object),
            fonction: expect.any(Object),
            commentaires: expect.any(Object),
          })
        );
      });
    });

    describe('getPersonnesRessources', () => {
      it('should return NewPersonnesRessources for default PersonnesRessources initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPersonnesRessourcesFormGroup(sampleWithNewData);

        const personnesRessources = service.getPersonnesRessources(formGroup) as any;

        expect(personnesRessources).toMatchObject(sampleWithNewData);
      });

      it('should return NewPersonnesRessources for empty PersonnesRessources initial value', () => {
        const formGroup = service.createPersonnesRessourcesFormGroup();

        const personnesRessources = service.getPersonnesRessources(formGroup) as any;

        expect(personnesRessources).toMatchObject({});
      });

      it('should return IPersonnesRessources', () => {
        const formGroup = service.createPersonnesRessourcesFormGroup(sampleWithRequiredData);

        const personnesRessources = service.getPersonnesRessources(formGroup) as any;

        expect(personnesRessources).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPersonnesRessources should not enable id FormControl', () => {
        const formGroup = service.createPersonnesRessourcesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPersonnesRessources should disable id FormControl', () => {
        const formGroup = service.createPersonnesRessourcesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
