import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../avis-generaux.test-samples';

import { AvisGenerauxFormService } from './avis-generaux-form.service';

describe('AvisGeneraux Form Service', () => {
  let service: AvisGenerauxFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AvisGenerauxFormService);
  });

  describe('Service methods', () => {
    describe('createAvisGenerauxFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAvisGenerauxFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            numero: expect.any(Object),
            annee: expect.any(Object),
            description: expect.any(Object),
            fichierAvis: expect.any(Object),
            version: expect.any(Object),
            lastVersionValid: expect.any(Object),
            etat: expect.any(Object),
            datePublication: expect.any(Object),
          })
        );
      });

      it('passing IAvisGeneraux should create a new form with FormGroup', () => {
        const formGroup = service.createAvisGenerauxFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            numero: expect.any(Object),
            annee: expect.any(Object),
            description: expect.any(Object),
            fichierAvis: expect.any(Object),
            version: expect.any(Object),
            lastVersionValid: expect.any(Object),
            etat: expect.any(Object),
            datePublication: expect.any(Object),
          })
        );
      });
    });

    describe('getAvisGeneraux', () => {
      it('should return NewAvisGeneraux for default AvisGeneraux initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAvisGenerauxFormGroup(sampleWithNewData);

        const avisGeneraux = service.getAvisGeneraux(formGroup) as any;

        expect(avisGeneraux).toMatchObject(sampleWithNewData);
      });

      it('should return NewAvisGeneraux for empty AvisGeneraux initial value', () => {
        const formGroup = service.createAvisGenerauxFormGroup();

        const avisGeneraux = service.getAvisGeneraux(formGroup) as any;

        expect(avisGeneraux).toMatchObject({});
      });

      it('should return IAvisGeneraux', () => {
        const formGroup = service.createAvisGenerauxFormGroup(sampleWithRequiredData);

        const avisGeneraux = service.getAvisGeneraux(formGroup) as any;

        expect(avisGeneraux).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAvisGeneraux should not enable id FormControl', () => {
        const formGroup = service.createAvisGenerauxFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAvisGeneraux should disable id FormControl', () => {
        const formGroup = service.createAvisGenerauxFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
