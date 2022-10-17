import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../jours-feries.test-samples';

import { JoursFeriesFormService } from './jours-feries-form.service';

describe('JoursFeries Form Service', () => {
  let service: JoursFeriesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JoursFeriesFormService);
  });

  describe('Service methods', () => {
    describe('createJoursFeriesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createJoursFeriesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            date: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });

      it('passing IJoursFeries should create a new form with FormGroup', () => {
        const formGroup = service.createJoursFeriesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            date: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });
    });

    describe('getJoursFeries', () => {
      it('should return NewJoursFeries for default JoursFeries initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createJoursFeriesFormGroup(sampleWithNewData);

        const joursFeries = service.getJoursFeries(formGroup) as any;

        expect(joursFeries).toMatchObject(sampleWithNewData);
      });

      it('should return NewJoursFeries for empty JoursFeries initial value', () => {
        const formGroup = service.createJoursFeriesFormGroup();

        const joursFeries = service.getJoursFeries(formGroup) as any;

        expect(joursFeries).toMatchObject({});
      });

      it('should return IJoursFeries', () => {
        const formGroup = service.createJoursFeriesFormGroup(sampleWithRequiredData);

        const joursFeries = service.getJoursFeries(formGroup) as any;

        expect(joursFeries).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IJoursFeries should not enable id FormControl', () => {
        const formGroup = service.createJoursFeriesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewJoursFeries should disable id FormControl', () => {
        const formGroup = service.createJoursFeriesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
