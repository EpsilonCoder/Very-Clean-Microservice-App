import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISygAutoriteContractante } from '../syg-autorite-contractante.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../syg-autorite-contractante.test-samples';

import { SygAutoriteContractanteService } from './syg-autorite-contractante.service';

const requireRestSample: ISygAutoriteContractante = {
  ...sampleWithRequiredData,
};

describe('SygAutoriteContractante Service', () => {
  let service: SygAutoriteContractanteService;
  let httpMock: HttpTestingController;
  let expectedResult: ISygAutoriteContractante | ISygAutoriteContractante[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SygAutoriteContractanteService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a SygAutoriteContractante', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const sygAutoriteContractante = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(sygAutoriteContractante).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SygAutoriteContractante', () => {
      const sygAutoriteContractante = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(sygAutoriteContractante).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SygAutoriteContractante', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SygAutoriteContractante', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SygAutoriteContractante', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSygAutoriteContractanteToCollectionIfMissing', () => {
      it('should add a SygAutoriteContractante to an empty array', () => {
        const sygAutoriteContractante: ISygAutoriteContractante = sampleWithRequiredData;
        expectedResult = service.addSygAutoriteContractanteToCollectionIfMissing([], sygAutoriteContractante);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sygAutoriteContractante);
      });

      it('should not add a SygAutoriteContractante to an array that contains it', () => {
        const sygAutoriteContractante: ISygAutoriteContractante = sampleWithRequiredData;
        const sygAutoriteContractanteCollection: ISygAutoriteContractante[] = [
          {
            ...sygAutoriteContractante,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSygAutoriteContractanteToCollectionIfMissing(
          sygAutoriteContractanteCollection,
          sygAutoriteContractante
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SygAutoriteContractante to an array that doesn't contain it", () => {
        const sygAutoriteContractante: ISygAutoriteContractante = sampleWithRequiredData;
        const sygAutoriteContractanteCollection: ISygAutoriteContractante[] = [sampleWithPartialData];
        expectedResult = service.addSygAutoriteContractanteToCollectionIfMissing(
          sygAutoriteContractanteCollection,
          sygAutoriteContractante
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sygAutoriteContractante);
      });

      it('should add only unique SygAutoriteContractante to an array', () => {
        const sygAutoriteContractanteArray: ISygAutoriteContractante[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const sygAutoriteContractanteCollection: ISygAutoriteContractante[] = [sampleWithRequiredData];
        expectedResult = service.addSygAutoriteContractanteToCollectionIfMissing(
          sygAutoriteContractanteCollection,
          ...sygAutoriteContractanteArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const sygAutoriteContractante: ISygAutoriteContractante = sampleWithRequiredData;
        const sygAutoriteContractante2: ISygAutoriteContractante = sampleWithPartialData;
        expectedResult = service.addSygAutoriteContractanteToCollectionIfMissing([], sygAutoriteContractante, sygAutoriteContractante2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sygAutoriteContractante);
        expect(expectedResult).toContain(sygAutoriteContractante2);
      });

      it('should accept null and undefined values', () => {
        const sygAutoriteContractante: ISygAutoriteContractante = sampleWithRequiredData;
        expectedResult = service.addSygAutoriteContractanteToCollectionIfMissing([], null, sygAutoriteContractante, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sygAutoriteContractante);
      });

      it('should return initial array if no SygAutoriteContractante is added', () => {
        const sygAutoriteContractanteCollection: ISygAutoriteContractante[] = [sampleWithRequiredData];
        expectedResult = service.addSygAutoriteContractanteToCollectionIfMissing(sygAutoriteContractanteCollection, undefined, null);
        expect(expectedResult).toEqual(sygAutoriteContractanteCollection);
      });
    });

    describe('compareSygAutoriteContractante', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSygAutoriteContractante(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSygAutoriteContractante(entity1, entity2);
        const compareResult2 = service.compareSygAutoriteContractante(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSygAutoriteContractante(entity1, entity2);
        const compareResult2 = service.compareSygAutoriteContractante(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSygAutoriteContractante(entity1, entity2);
        const compareResult2 = service.compareSygAutoriteContractante(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
