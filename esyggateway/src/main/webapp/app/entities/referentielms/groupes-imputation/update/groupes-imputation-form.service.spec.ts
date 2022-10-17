import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../groupes-imputation.test-samples';

import { GroupesImputationFormService } from './groupes-imputation-form.service';

describe('GroupesImputation Form Service', () => {
  let service: GroupesImputationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GroupesImputationFormService);
  });

  describe('Service methods', () => {
    describe('createGroupesImputationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createGroupesImputationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            libelle: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });

      it('passing IGroupesImputation should create a new form with FormGroup', () => {
        const formGroup = service.createGroupesImputationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            libelle: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });
    });

    describe('getGroupesImputation', () => {
      it('should return NewGroupesImputation for default GroupesImputation initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createGroupesImputationFormGroup(sampleWithNewData);

        const groupesImputation = service.getGroupesImputation(formGroup) as any;

        expect(groupesImputation).toMatchObject(sampleWithNewData);
      });

      it('should return NewGroupesImputation for empty GroupesImputation initial value', () => {
        const formGroup = service.createGroupesImputationFormGroup();

        const groupesImputation = service.getGroupesImputation(formGroup) as any;

        expect(groupesImputation).toMatchObject({});
      });

      it('should return IGroupesImputation', () => {
        const formGroup = service.createGroupesImputationFormGroup(sampleWithRequiredData);

        const groupesImputation = service.getGroupesImputation(formGroup) as any;

        expect(groupesImputation).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IGroupesImputation should not enable id FormControl', () => {
        const formGroup = service.createGroupesImputationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewGroupesImputation should disable id FormControl', () => {
        const formGroup = service.createGroupesImputationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
