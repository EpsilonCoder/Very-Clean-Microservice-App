import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../syg-autorite-contractante.test-samples';

import { SygAutoriteContractanteFormService } from './syg-autorite-contractante-form.service';

describe('SygAutoriteContractante Form Service', () => {
  let service: SygAutoriteContractanteFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SygAutoriteContractanteFormService);
  });

  describe('Service methods', () => {
    describe('createSygAutoriteContractanteFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSygAutoriteContractanteFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ordre: expect.any(Object),
            denomination: expect.any(Object),
            responsable: expect.any(Object),
            adresse: expect.any(Object),
            telephone: expect.any(Object),
            fax: expect.any(Object),
            email: expect.any(Object),
            sigle: expect.any(Object),
            urlsiteweb: expect.any(Object),
            approbation: expect.any(Object),
            logo: expect.any(Object),
            typeAutoriteContractante: expect.any(Object),
          })
        );
      });

      it('passing ISygAutoriteContractante should create a new form with FormGroup', () => {
        const formGroup = service.createSygAutoriteContractanteFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ordre: expect.any(Object),
            denomination: expect.any(Object),
            responsable: expect.any(Object),
            adresse: expect.any(Object),
            telephone: expect.any(Object),
            fax: expect.any(Object),
            email: expect.any(Object),
            sigle: expect.any(Object),
            urlsiteweb: expect.any(Object),
            approbation: expect.any(Object),
            logo: expect.any(Object),
            typeAutoriteContractante: expect.any(Object),
          })
        );
      });
    });

    describe('getSygAutoriteContractante', () => {
      it('should return NewSygAutoriteContractante for default SygAutoriteContractante initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSygAutoriteContractanteFormGroup(sampleWithNewData);

        const sygAutoriteContractante = service.getSygAutoriteContractante(formGroup) as any;

        expect(sygAutoriteContractante).toMatchObject(sampleWithNewData);
      });

      it('should return NewSygAutoriteContractante for empty SygAutoriteContractante initial value', () => {
        const formGroup = service.createSygAutoriteContractanteFormGroup();

        const sygAutoriteContractante = service.getSygAutoriteContractante(formGroup) as any;

        expect(sygAutoriteContractante).toMatchObject({});
      });

      it('should return ISygAutoriteContractante', () => {
        const formGroup = service.createSygAutoriteContractanteFormGroup(sampleWithRequiredData);

        const sygAutoriteContractante = service.getSygAutoriteContractante(formGroup) as any;

        expect(sygAutoriteContractante).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISygAutoriteContractante should not enable id FormControl', () => {
        const formGroup = service.createSygAutoriteContractanteFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSygAutoriteContractante should disable id FormControl', () => {
        const formGroup = service.createSygAutoriteContractanteFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
