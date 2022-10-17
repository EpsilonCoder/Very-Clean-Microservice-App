import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAvisGeneraux } from '../avis-generaux.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../avis-generaux.test-samples';

import { AvisGenerauxService, RestAvisGeneraux } from './avis-generaux.service';

const requireRestSample: RestAvisGeneraux = {
  ...sampleWithRequiredData,
  datePublication: sampleWithRequiredData.datePublication?.toJSON(),
};

describe('AvisGeneraux Service', () => {
  let service: AvisGenerauxService;
  let httpMock: HttpTestingController;
  let expectedResult: IAvisGeneraux | IAvisGeneraux[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AvisGenerauxService);
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

    it('should create a AvisGeneraux', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const avisGeneraux = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(avisGeneraux).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AvisGeneraux', () => {
      const avisGeneraux = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(avisGeneraux).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AvisGeneraux', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AvisGeneraux', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AvisGeneraux', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAvisGenerauxToCollectionIfMissing', () => {
      it('should add a AvisGeneraux to an empty array', () => {
        const avisGeneraux: IAvisGeneraux = sampleWithRequiredData;
        expectedResult = service.addAvisGenerauxToCollectionIfMissing([], avisGeneraux);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(avisGeneraux);
      });

      it('should not add a AvisGeneraux to an array that contains it', () => {
        const avisGeneraux: IAvisGeneraux = sampleWithRequiredData;
        const avisGenerauxCollection: IAvisGeneraux[] = [
          {
            ...avisGeneraux,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAvisGenerauxToCollectionIfMissing(avisGenerauxCollection, avisGeneraux);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AvisGeneraux to an array that doesn't contain it", () => {
        const avisGeneraux: IAvisGeneraux = sampleWithRequiredData;
        const avisGenerauxCollection: IAvisGeneraux[] = [sampleWithPartialData];
        expectedResult = service.addAvisGenerauxToCollectionIfMissing(avisGenerauxCollection, avisGeneraux);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(avisGeneraux);
      });

      it('should add only unique AvisGeneraux to an array', () => {
        const avisGenerauxArray: IAvisGeneraux[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const avisGenerauxCollection: IAvisGeneraux[] = [sampleWithRequiredData];
        expectedResult = service.addAvisGenerauxToCollectionIfMissing(avisGenerauxCollection, ...avisGenerauxArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const avisGeneraux: IAvisGeneraux = sampleWithRequiredData;
        const avisGeneraux2: IAvisGeneraux = sampleWithPartialData;
        expectedResult = service.addAvisGenerauxToCollectionIfMissing([], avisGeneraux, avisGeneraux2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(avisGeneraux);
        expect(expectedResult).toContain(avisGeneraux2);
      });

      it('should accept null and undefined values', () => {
        const avisGeneraux: IAvisGeneraux = sampleWithRequiredData;
        expectedResult = service.addAvisGenerauxToCollectionIfMissing([], null, avisGeneraux, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(avisGeneraux);
      });

      it('should return initial array if no AvisGeneraux is added', () => {
        const avisGenerauxCollection: IAvisGeneraux[] = [sampleWithRequiredData];
        expectedResult = service.addAvisGenerauxToCollectionIfMissing(avisGenerauxCollection, undefined, null);
        expect(expectedResult).toEqual(avisGenerauxCollection);
      });
    });

    describe('compareAvisGeneraux', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAvisGeneraux(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAvisGeneraux(entity1, entity2);
        const compareResult2 = service.compareAvisGeneraux(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAvisGeneraux(entity1, entity2);
        const compareResult2 = service.compareAvisGeneraux(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAvisGeneraux(entity1, entity2);
        const compareResult2 = service.compareAvisGeneraux(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
