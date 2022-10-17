import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../criteres-qualification.test-samples';

import { CriteresQualificationFormService } from './criteres-qualification-form.service';

describe('CriteresQualification Form Service', () => {
  let service: CriteresQualificationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CriteresQualificationFormService);
  });

  describe('Service methods', () => {
    describe('createCriteresQualificationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCriteresQualificationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            libelle: expect.any(Object),
          })
        );
      });

      it('passing ICriteresQualification should create a new form with FormGroup', () => {
        const formGroup = service.createCriteresQualificationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            libelle: expect.any(Object),
          })
        );
      });
    });

    describe('getCriteresQualification', () => {
      it('should return NewCriteresQualification for default CriteresQualification initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCriteresQualificationFormGroup(sampleWithNewData);

        const criteresQualification = service.getCriteresQualification(formGroup) as any;

        expect(criteresQualification).toMatchObject(sampleWithNewData);
      });

      it('should return NewCriteresQualification for empty CriteresQualification initial value', () => {
        const formGroup = service.createCriteresQualificationFormGroup();

        const criteresQualification = service.getCriteresQualification(formGroup) as any;

        expect(criteresQualification).toMatchObject({});
      });

      it('should return ICriteresQualification', () => {
        const formGroup = service.createCriteresQualificationFormGroup(sampleWithRequiredData);

        const criteresQualification = service.getCriteresQualification(formGroup) as any;

        expect(criteresQualification).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICriteresQualification should not enable id FormControl', () => {
        const formGroup = service.createCriteresQualificationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCriteresQualification should disable id FormControl', () => {
        const formGroup = service.createCriteresQualificationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
