import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../specialites-personnel.test-samples';

import { SpecialitesPersonnelFormService } from './specialites-personnel-form.service';

describe('SpecialitesPersonnel Form Service', () => {
  let service: SpecialitesPersonnelFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SpecialitesPersonnelFormService);
  });

  describe('Service methods', () => {
    describe('createSpecialitesPersonnelFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSpecialitesPersonnelFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            libelle: expect.any(Object),
          })
        );
      });

      it('passing ISpecialitesPersonnel should create a new form with FormGroup', () => {
        const formGroup = service.createSpecialitesPersonnelFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            libelle: expect.any(Object),
          })
        );
      });
    });

    describe('getSpecialitesPersonnel', () => {
      it('should return NewSpecialitesPersonnel for default SpecialitesPersonnel initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSpecialitesPersonnelFormGroup(sampleWithNewData);

        const specialitesPersonnel = service.getSpecialitesPersonnel(formGroup) as any;

        expect(specialitesPersonnel).toMatchObject(sampleWithNewData);
      });

      it('should return NewSpecialitesPersonnel for empty SpecialitesPersonnel initial value', () => {
        const formGroup = service.createSpecialitesPersonnelFormGroup();

        const specialitesPersonnel = service.getSpecialitesPersonnel(formGroup) as any;

        expect(specialitesPersonnel).toMatchObject({});
      });

      it('should return ISpecialitesPersonnel', () => {
        const formGroup = service.createSpecialitesPersonnelFormGroup(sampleWithRequiredData);

        const specialitesPersonnel = service.getSpecialitesPersonnel(formGroup) as any;

        expect(specialitesPersonnel).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISpecialitesPersonnel should not enable id FormControl', () => {
        const formGroup = service.createSpecialitesPersonnelFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSpecialitesPersonnel should disable id FormControl', () => {
        const formGroup = service.createSpecialitesPersonnelFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
