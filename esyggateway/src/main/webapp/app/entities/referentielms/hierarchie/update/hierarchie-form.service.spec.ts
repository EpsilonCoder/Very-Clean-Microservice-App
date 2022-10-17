import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../hierarchie.test-samples';

import { HierarchieFormService } from './hierarchie-form.service';

describe('Hierarchie Form Service', () => {
  let service: HierarchieFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HierarchieFormService);
  });

  describe('Service methods', () => {
    describe('createHierarchieFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createHierarchieFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            libelle: expect.any(Object),
          })
        );
      });

      it('passing IHierarchie should create a new form with FormGroup', () => {
        const formGroup = service.createHierarchieFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            libelle: expect.any(Object),
          })
        );
      });
    });

    describe('getHierarchie', () => {
      it('should return NewHierarchie for default Hierarchie initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createHierarchieFormGroup(sampleWithNewData);

        const hierarchie = service.getHierarchie(formGroup) as any;

        expect(hierarchie).toMatchObject(sampleWithNewData);
      });

      it('should return NewHierarchie for empty Hierarchie initial value', () => {
        const formGroup = service.createHierarchieFormGroup();

        const hierarchie = service.getHierarchie(formGroup) as any;

        expect(hierarchie).toMatchObject({});
      });

      it('should return IHierarchie', () => {
        const formGroup = service.createHierarchieFormGroup(sampleWithRequiredData);

        const hierarchie = service.getHierarchie(formGroup) as any;

        expect(hierarchie).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IHierarchie should not enable id FormControl', () => {
        const formGroup = service.createHierarchieFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewHierarchie should disable id FormControl', () => {
        const formGroup = service.createHierarchieFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
