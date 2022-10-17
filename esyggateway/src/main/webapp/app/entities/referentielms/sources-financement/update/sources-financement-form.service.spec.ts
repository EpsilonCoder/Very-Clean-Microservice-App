import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../sources-financement.test-samples';

import { SourcesFinancementFormService } from './sources-financement-form.service';

describe('SourcesFinancement Form Service', () => {
  let service: SourcesFinancementFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SourcesFinancementFormService);
  });

  describe('Service methods', () => {
    describe('createSourcesFinancementFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSourcesFinancementFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            libelle: expect.any(Object),
            corbeille: expect.any(Object),
          })
        );
      });

      it('passing ISourcesFinancement should create a new form with FormGroup', () => {
        const formGroup = service.createSourcesFinancementFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            libelle: expect.any(Object),
            corbeille: expect.any(Object),
          })
        );
      });
    });

    describe('getSourcesFinancement', () => {
      it('should return NewSourcesFinancement for default SourcesFinancement initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSourcesFinancementFormGroup(sampleWithNewData);

        const sourcesFinancement = service.getSourcesFinancement(formGroup) as any;

        expect(sourcesFinancement).toMatchObject(sampleWithNewData);
      });

      it('should return NewSourcesFinancement for empty SourcesFinancement initial value', () => {
        const formGroup = service.createSourcesFinancementFormGroup();

        const sourcesFinancement = service.getSourcesFinancement(formGroup) as any;

        expect(sourcesFinancement).toMatchObject({});
      });

      it('should return ISourcesFinancement', () => {
        const formGroup = service.createSourcesFinancementFormGroup(sampleWithRequiredData);

        const sourcesFinancement = service.getSourcesFinancement(formGroup) as any;

        expect(sourcesFinancement).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISourcesFinancement should not enable id FormControl', () => {
        const formGroup = service.createSourcesFinancementFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSourcesFinancement should disable id FormControl', () => {
        const formGroup = service.createSourcesFinancementFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
