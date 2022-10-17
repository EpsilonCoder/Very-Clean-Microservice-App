import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAutoriteContractante } from '../autorite-contractante.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../autorite-contractante.test-samples';

import { AutoriteContractanteService } from './autorite-contractante.service';

const requireRestSample: IAutoriteContractante = {
  ...sampleWithRequiredData,
};

describe('AutoriteContractante Service', () => {
  let service: AutoriteContractanteService;
  let httpMock: HttpTestingController;
  let expectedResult: IAutoriteContractante | IAutoriteContractante[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AutoriteContractanteService);
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

    it('should return a list of AutoriteContractante', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    describe('addAutoriteContractanteToCollectionIfMissing', () => {
      it('should add a AutoriteContractante to an empty array', () => {
        const autoriteContractante: IAutoriteContractante = sampleWithRequiredData;
        expectedResult = service.addAutoriteContractanteToCollectionIfMissing([], autoriteContractante);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(autoriteContractante);
      });

      it('should not add a AutoriteContractante to an array that contains it', () => {
        const autoriteContractante: IAutoriteContractante = sampleWithRequiredData;
        const autoriteContractanteCollection: IAutoriteContractante[] = [
          {
            ...autoriteContractante,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAutoriteContractanteToCollectionIfMissing(autoriteContractanteCollection, autoriteContractante);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AutoriteContractante to an array that doesn't contain it", () => {
        const autoriteContractante: IAutoriteContractante = sampleWithRequiredData;
        const autoriteContractanteCollection: IAutoriteContractante[] = [sampleWithPartialData];
        expectedResult = service.addAutoriteContractanteToCollectionIfMissing(autoriteContractanteCollection, autoriteContractante);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(autoriteContractante);
      });

      it('should add only unique AutoriteContractante to an array', () => {
        const autoriteContractanteArray: IAutoriteContractante[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const autoriteContractanteCollection: IAutoriteContractante[] = [sampleWithRequiredData];
        expectedResult = service.addAutoriteContractanteToCollectionIfMissing(autoriteContractanteCollection, ...autoriteContractanteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const autoriteContractante: IAutoriteContractante = sampleWithRequiredData;
        const autoriteContractante2: IAutoriteContractante = sampleWithPartialData;
        expectedResult = service.addAutoriteContractanteToCollectionIfMissing([], autoriteContractante, autoriteContractante2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(autoriteContractante);
        expect(expectedResult).toContain(autoriteContractante2);
      });

      it('should accept null and undefined values', () => {
        const autoriteContractante: IAutoriteContractante = sampleWithRequiredData;
        expectedResult = service.addAutoriteContractanteToCollectionIfMissing([], null, autoriteContractante, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(autoriteContractante);
      });

      it('should return initial array if no AutoriteContractante is added', () => {
        const autoriteContractanteCollection: IAutoriteContractante[] = [sampleWithRequiredData];
        expectedResult = service.addAutoriteContractanteToCollectionIfMissing(autoriteContractanteCollection, undefined, null);
        expect(expectedResult).toEqual(autoriteContractanteCollection);
      });
    });

    describe('compareAutoriteContractante', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAutoriteContractante(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAutoriteContractante(entity1, entity2);
        const compareResult2 = service.compareAutoriteContractante(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAutoriteContractante(entity1, entity2);
        const compareResult2 = service.compareAutoriteContractante(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAutoriteContractante(entity1, entity2);
        const compareResult2 = service.compareAutoriteContractante(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
