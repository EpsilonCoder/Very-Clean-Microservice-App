import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../categorie-fournisseur.test-samples';

import { CategorieFournisseurFormService } from './categorie-fournisseur-form.service';

describe('CategorieFournisseur Form Service', () => {
  let service: CategorieFournisseurFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CategorieFournisseurFormService);
  });

  describe('Service methods', () => {
    describe('createCategorieFournisseurFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCategorieFournisseurFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            libelle: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });

      it('passing ICategorieFournisseur should create a new form with FormGroup', () => {
        const formGroup = service.createCategorieFournisseurFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            libelle: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });
    });

    describe('getCategorieFournisseur', () => {
      it('should return NewCategorieFournisseur for default CategorieFournisseur initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCategorieFournisseurFormGroup(sampleWithNewData);

        const categorieFournisseur = service.getCategorieFournisseur(formGroup) as any;

        expect(categorieFournisseur).toMatchObject(sampleWithNewData);
      });

      it('should return NewCategorieFournisseur for empty CategorieFournisseur initial value', () => {
        const formGroup = service.createCategorieFournisseurFormGroup();

        const categorieFournisseur = service.getCategorieFournisseur(formGroup) as any;

        expect(categorieFournisseur).toMatchObject({});
      });

      it('should return ICategorieFournisseur', () => {
        const formGroup = service.createCategorieFournisseurFormGroup(sampleWithRequiredData);

        const categorieFournisseur = service.getCategorieFournisseur(formGroup) as any;

        expect(categorieFournisseur).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICategorieFournisseur should not enable id FormControl', () => {
        const formGroup = service.createCategorieFournisseurFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCategorieFournisseur should disable id FormControl', () => {
        const formGroup = service.createCategorieFournisseurFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
