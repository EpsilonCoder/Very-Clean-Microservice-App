import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../pieces-administratives.test-samples';

import { PiecesAdministrativesFormService } from './pieces-administratives-form.service';

describe('PiecesAdministratives Form Service', () => {
  let service: PiecesAdministrativesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PiecesAdministrativesFormService);
  });

  describe('Service methods', () => {
    describe('createPiecesAdministrativesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPiecesAdministrativesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            libelle: expect.any(Object),
            description: expect.any(Object),
            localisation: expect.any(Object),
            type: expect.any(Object),
          })
        );
      });

      it('passing IPiecesAdministratives should create a new form with FormGroup', () => {
        const formGroup = service.createPiecesAdministrativesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            libelle: expect.any(Object),
            description: expect.any(Object),
            localisation: expect.any(Object),
            type: expect.any(Object),
          })
        );
      });
    });

    describe('getPiecesAdministratives', () => {
      it('should return NewPiecesAdministratives for default PiecesAdministratives initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPiecesAdministrativesFormGroup(sampleWithNewData);

        const piecesAdministratives = service.getPiecesAdministratives(formGroup) as any;

        expect(piecesAdministratives).toMatchObject(sampleWithNewData);
      });

      it('should return NewPiecesAdministratives for empty PiecesAdministratives initial value', () => {
        const formGroup = service.createPiecesAdministrativesFormGroup();

        const piecesAdministratives = service.getPiecesAdministratives(formGroup) as any;

        expect(piecesAdministratives).toMatchObject({});
      });

      it('should return IPiecesAdministratives', () => {
        const formGroup = service.createPiecesAdministrativesFormGroup(sampleWithRequiredData);

        const piecesAdministratives = service.getPiecesAdministratives(formGroup) as any;

        expect(piecesAdministratives).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPiecesAdministratives should not enable id FormControl', () => {
        const formGroup = service.createPiecesAdministrativesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPiecesAdministratives should disable id FormControl', () => {
        const formGroup = service.createPiecesAdministrativesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
