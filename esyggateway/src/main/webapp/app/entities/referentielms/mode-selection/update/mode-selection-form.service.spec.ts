import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../mode-selection.test-samples';

import { ModeSelectionFormService } from './mode-selection-form.service';

describe('ModeSelection Form Service', () => {
  let service: ModeSelectionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ModeSelectionFormService);
  });

  describe('Service methods', () => {
    describe('createModeSelectionFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createModeSelectionFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            libelle: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });

      it('passing IModeSelection should create a new form with FormGroup', () => {
        const formGroup = service.createModeSelectionFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            libelle: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });
    });

    describe('getModeSelection', () => {
      it('should return NewModeSelection for default ModeSelection initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createModeSelectionFormGroup(sampleWithNewData);

        const modeSelection = service.getModeSelection(formGroup) as any;

        expect(modeSelection).toMatchObject(sampleWithNewData);
      });

      it('should return NewModeSelection for empty ModeSelection initial value', () => {
        const formGroup = service.createModeSelectionFormGroup();

        const modeSelection = service.getModeSelection(formGroup) as any;

        expect(modeSelection).toMatchObject({});
      });

      it('should return IModeSelection', () => {
        const formGroup = service.createModeSelectionFormGroup(sampleWithRequiredData);

        const modeSelection = service.getModeSelection(formGroup) as any;

        expect(modeSelection).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IModeSelection should not enable id FormControl', () => {
        const formGroup = service.createModeSelectionFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewModeSelection should disable id FormControl', () => {
        const formGroup = service.createModeSelectionFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
