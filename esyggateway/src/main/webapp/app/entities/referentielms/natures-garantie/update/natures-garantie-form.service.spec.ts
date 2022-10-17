import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../natures-garantie.test-samples';

import { NaturesGarantieFormService } from './natures-garantie-form.service';

describe('NaturesGarantie Form Service', () => {
  let service: NaturesGarantieFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NaturesGarantieFormService);
  });

  describe('Service methods', () => {
    describe('createNaturesGarantieFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createNaturesGarantieFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            libelle: expect.any(Object),
          })
        );
      });

      it('passing INaturesGarantie should create a new form with FormGroup', () => {
        const formGroup = service.createNaturesGarantieFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            libelle: expect.any(Object),
          })
        );
      });
    });

    describe('getNaturesGarantie', () => {
      it('should return NewNaturesGarantie for default NaturesGarantie initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createNaturesGarantieFormGroup(sampleWithNewData);

        const naturesGarantie = service.getNaturesGarantie(formGroup) as any;

        expect(naturesGarantie).toMatchObject(sampleWithNewData);
      });

      it('should return NewNaturesGarantie for empty NaturesGarantie initial value', () => {
        const formGroup = service.createNaturesGarantieFormGroup();

        const naturesGarantie = service.getNaturesGarantie(formGroup) as any;

        expect(naturesGarantie).toMatchObject({});
      });

      it('should return INaturesGarantie', () => {
        const formGroup = service.createNaturesGarantieFormGroup(sampleWithRequiredData);

        const naturesGarantie = service.getNaturesGarantie(formGroup) as any;

        expect(naturesGarantie).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing INaturesGarantie should not enable id FormControl', () => {
        const formGroup = service.createNaturesGarantieFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewNaturesGarantie should disable id FormControl', () => {
        const formGroup = service.createNaturesGarantieFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
