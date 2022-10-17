import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../delais.test-samples';

import { DelaisFormService } from './delais-form.service';

describe('Delais Form Service', () => {
  let service: DelaisFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DelaisFormService);
  });

  describe('Service methods', () => {
    describe('createDelaisFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDelaisFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            libelle: expect.any(Object),
            code: expect.any(Object),
            unite: expect.any(Object),
            valeur: expect.any(Object),
            debutValidite: expect.any(Object),
            finValidite: expect.any(Object),
            commentaires: expect.any(Object),
          })
        );
      });

      it('passing IDelais should create a new form with FormGroup', () => {
        const formGroup = service.createDelaisFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            libelle: expect.any(Object),
            code: expect.any(Object),
            unite: expect.any(Object),
            valeur: expect.any(Object),
            debutValidite: expect.any(Object),
            finValidite: expect.any(Object),
            commentaires: expect.any(Object),
          })
        );
      });
    });

    describe('getDelais', () => {
      it('should return NewDelais for default Delais initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDelaisFormGroup(sampleWithNewData);

        const delais = service.getDelais(formGroup) as any;

        expect(delais).toMatchObject(sampleWithNewData);
      });

      it('should return NewDelais for empty Delais initial value', () => {
        const formGroup = service.createDelaisFormGroup();

        const delais = service.getDelais(formGroup) as any;

        expect(delais).toMatchObject({});
      });

      it('should return IDelais', () => {
        const formGroup = service.createDelaisFormGroup(sampleWithRequiredData);

        const delais = service.getDelais(formGroup) as any;

        expect(delais).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDelais should not enable id FormControl', () => {
        const formGroup = service.createDelaisFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDelais should disable id FormControl', () => {
        const formGroup = service.createDelaisFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
