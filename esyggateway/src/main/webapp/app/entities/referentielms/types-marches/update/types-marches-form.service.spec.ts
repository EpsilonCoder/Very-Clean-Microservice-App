import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../types-marches.test-samples';

import { TypesMarchesFormService } from './types-marches-form.service';

describe('TypesMarches Form Service', () => {
  let service: TypesMarchesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TypesMarchesFormService);
  });

  describe('Service methods', () => {
    describe('createTypesMarchesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTypesMarchesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            libelle: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });

      it('passing ITypesMarches should create a new form with FormGroup', () => {
        const formGroup = service.createTypesMarchesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            libelle: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });
    });

    describe('getTypesMarches', () => {
      it('should return NewTypesMarches for default TypesMarches initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTypesMarchesFormGroup(sampleWithNewData);

        const typesMarches = service.getTypesMarches(formGroup) as any;

        expect(typesMarches).toMatchObject(sampleWithNewData);
      });

      it('should return NewTypesMarches for empty TypesMarches initial value', () => {
        const formGroup = service.createTypesMarchesFormGroup();

        const typesMarches = service.getTypesMarches(formGroup) as any;

        expect(typesMarches).toMatchObject({});
      });

      it('should return ITypesMarches', () => {
        const formGroup = service.createTypesMarchesFormGroup(sampleWithRequiredData);

        const typesMarches = service.getTypesMarches(formGroup) as any;

        expect(typesMarches).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITypesMarches should not enable id FormControl', () => {
        const formGroup = service.createTypesMarchesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTypesMarches should disable id FormControl', () => {
        const formGroup = service.createTypesMarchesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
