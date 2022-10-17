import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../configuration-taux.test-samples';

import { ConfigurationTauxFormService } from './configuration-taux-form.service';

describe('ConfigurationTaux Form Service', () => {
  let service: ConfigurationTauxFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ConfigurationTauxFormService);
  });

  describe('Service methods', () => {
    describe('createConfigurationTauxFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createConfigurationTauxFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            libelle: expect.any(Object),
            taux: expect.any(Object),
            dateDebut: expect.any(Object),
            dateFin: expect.any(Object),
            invalid: expect.any(Object),
            pays: expect.any(Object),
          })
        );
      });

      it('passing IConfigurationTaux should create a new form with FormGroup', () => {
        const formGroup = service.createConfigurationTauxFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            libelle: expect.any(Object),
            taux: expect.any(Object),
            dateDebut: expect.any(Object),
            dateFin: expect.any(Object),
            invalid: expect.any(Object),
            pays: expect.any(Object),
          })
        );
      });
    });

    describe('getConfigurationTaux', () => {
      it('should return NewConfigurationTaux for default ConfigurationTaux initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createConfigurationTauxFormGroup(sampleWithNewData);

        const configurationTaux = service.getConfigurationTaux(formGroup) as any;

        expect(configurationTaux).toMatchObject(sampleWithNewData);
      });

      it('should return NewConfigurationTaux for empty ConfigurationTaux initial value', () => {
        const formGroup = service.createConfigurationTauxFormGroup();

        const configurationTaux = service.getConfigurationTaux(formGroup) as any;

        expect(configurationTaux).toMatchObject({});
      });

      it('should return IConfigurationTaux', () => {
        const formGroup = service.createConfigurationTauxFormGroup(sampleWithRequiredData);

        const configurationTaux = service.getConfigurationTaux(formGroup) as any;

        expect(configurationTaux).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IConfigurationTaux should not enable id FormControl', () => {
        const formGroup = service.createConfigurationTauxFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewConfigurationTaux should disable id FormControl', () => {
        const formGroup = service.createConfigurationTauxFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
