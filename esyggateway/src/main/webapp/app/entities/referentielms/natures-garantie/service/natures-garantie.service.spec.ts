import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INaturesGarantie } from '../natures-garantie.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../natures-garantie.test-samples';

import { NaturesGarantieService } from './natures-garantie.service';

const requireRestSample: INaturesGarantie = {
  ...sampleWithRequiredData,
};

describe('NaturesGarantie Service', () => {
  let service: NaturesGarantieService;
  let httpMock: HttpTestingController;
  let expectedResult: INaturesGarantie | INaturesGarantie[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NaturesGarantieService);
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

    it('should create a NaturesGarantie', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const naturesGarantie = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(naturesGarantie).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NaturesGarantie', () => {
      const naturesGarantie = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(naturesGarantie).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a NaturesGarantie', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of NaturesGarantie', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a NaturesGarantie', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addNaturesGarantieToCollectionIfMissing', () => {
      it('should add a NaturesGarantie to an empty array', () => {
        const naturesGarantie: INaturesGarantie = sampleWithRequiredData;
        expectedResult = service.addNaturesGarantieToCollectionIfMissing([], naturesGarantie);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(naturesGarantie);
      });

      it('should not add a NaturesGarantie to an array that contains it', () => {
        const naturesGarantie: INaturesGarantie = sampleWithRequiredData;
        const naturesGarantieCollection: INaturesGarantie[] = [
          {
            ...naturesGarantie,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addNaturesGarantieToCollectionIfMissing(naturesGarantieCollection, naturesGarantie);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NaturesGarantie to an array that doesn't contain it", () => {
        const naturesGarantie: INaturesGarantie = sampleWithRequiredData;
        const naturesGarantieCollection: INaturesGarantie[] = [sampleWithPartialData];
        expectedResult = service.addNaturesGarantieToCollectionIfMissing(naturesGarantieCollection, naturesGarantie);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(naturesGarantie);
      });

      it('should add only unique NaturesGarantie to an array', () => {
        const naturesGarantieArray: INaturesGarantie[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const naturesGarantieCollection: INaturesGarantie[] = [sampleWithRequiredData];
        expectedResult = service.addNaturesGarantieToCollectionIfMissing(naturesGarantieCollection, ...naturesGarantieArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const naturesGarantie: INaturesGarantie = sampleWithRequiredData;
        const naturesGarantie2: INaturesGarantie = sampleWithPartialData;
        expectedResult = service.addNaturesGarantieToCollectionIfMissing([], naturesGarantie, naturesGarantie2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(naturesGarantie);
        expect(expectedResult).toContain(naturesGarantie2);
      });

      it('should accept null and undefined values', () => {
        const naturesGarantie: INaturesGarantie = sampleWithRequiredData;
        expectedResult = service.addNaturesGarantieToCollectionIfMissing([], null, naturesGarantie, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(naturesGarantie);
      });

      it('should return initial array if no NaturesGarantie is added', () => {
        const naturesGarantieCollection: INaturesGarantie[] = [sampleWithRequiredData];
        expectedResult = service.addNaturesGarantieToCollectionIfMissing(naturesGarantieCollection, undefined, null);
        expect(expectedResult).toEqual(naturesGarantieCollection);
      });
    });

    describe('compareNaturesGarantie', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareNaturesGarantie(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareNaturesGarantie(entity1, entity2);
        const compareResult2 = service.compareNaturesGarantie(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareNaturesGarantie(entity1, entity2);
        const compareResult2 = service.compareNaturesGarantie(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareNaturesGarantie(entity1, entity2);
        const compareResult2 = service.compareNaturesGarantie(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
