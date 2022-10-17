import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../situation-matrimoniale.test-samples';

import { SituationMatrimonialeFormService } from './situation-matrimoniale-form.service';

describe('SituationMatrimoniale Form Service', () => {
  let service: SituationMatrimonialeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SituationMatrimonialeFormService);
  });

  describe('Service methods', () => {
    describe('createSituationMatrimonialeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSituationMatrimonialeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            libelle: expect.any(Object),
          })
        );
      });

      it('passing ISituationMatrimoniale should create a new form with FormGroup', () => {
        const formGroup = service.createSituationMatrimonialeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            libelle: expect.any(Object),
          })
        );
      });
    });

    describe('getSituationMatrimoniale', () => {
      it('should return NewSituationMatrimoniale for default SituationMatrimoniale initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSituationMatrimonialeFormGroup(sampleWithNewData);

        const situationMatrimoniale = service.getSituationMatrimoniale(formGroup) as any;

        expect(situationMatrimoniale).toMatchObject(sampleWithNewData);
      });

      it('should return NewSituationMatrimoniale for empty SituationMatrimoniale initial value', () => {
        const formGroup = service.createSituationMatrimonialeFormGroup();

        const situationMatrimoniale = service.getSituationMatrimoniale(formGroup) as any;

        expect(situationMatrimoniale).toMatchObject({});
      });

      it('should return ISituationMatrimoniale', () => {
        const formGroup = service.createSituationMatrimonialeFormGroup(sampleWithRequiredData);

        const situationMatrimoniale = service.getSituationMatrimoniale(formGroup) as any;

        expect(situationMatrimoniale).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISituationMatrimoniale should not enable id FormControl', () => {
        const formGroup = service.createSituationMatrimonialeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSituationMatrimoniale should disable id FormControl', () => {
        const formGroup = service.createSituationMatrimonialeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
