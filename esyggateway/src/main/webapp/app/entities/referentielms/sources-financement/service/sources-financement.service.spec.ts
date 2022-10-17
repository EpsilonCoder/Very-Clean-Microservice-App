import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISourcesFinancement } from '../sources-financement.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../sources-financement.test-samples';

import { SourcesFinancementService } from './sources-financement.service';

const requireRestSample: ISourcesFinancement = {
  ...sampleWithRequiredData,
};

describe('SourcesFinancement Service', () => {
  let service: SourcesFinancementService;
  let httpMock: HttpTestingController;
  let expectedResult: ISourcesFinancement | ISourcesFinancement[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SourcesFinancementService);
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

    it('should create a SourcesFinancement', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const sourcesFinancement = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(sourcesFinancement).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SourcesFinancement', () => {
      const sourcesFinancement = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(sourcesFinancement).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SourcesFinancement', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SourcesFinancement', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SourcesFinancement', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSourcesFinancementToCollectionIfMissing', () => {
      it('should add a SourcesFinancement to an empty array', () => {
        const sourcesFinancement: ISourcesFinancement = sampleWithRequiredData;
        expectedResult = service.addSourcesFinancementToCollectionIfMissing([], sourcesFinancement);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sourcesFinancement);
      });

      it('should not add a SourcesFinancement to an array that contains it', () => {
        const sourcesFinancement: ISourcesFinancement = sampleWithRequiredData;
        const sourcesFinancementCollection: ISourcesFinancement[] = [
          {
            ...sourcesFinancement,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSourcesFinancementToCollectionIfMissing(sourcesFinancementCollection, sourcesFinancement);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SourcesFinancement to an array that doesn't contain it", () => {
        const sourcesFinancement: ISourcesFinancement = sampleWithRequiredData;
        const sourcesFinancementCollection: ISourcesFinancement[] = [sampleWithPartialData];
        expectedResult = service.addSourcesFinancementToCollectionIfMissing(sourcesFinancementCollection, sourcesFinancement);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sourcesFinancement);
      });

      it('should add only unique SourcesFinancement to an array', () => {
        const sourcesFinancementArray: ISourcesFinancement[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const sourcesFinancementCollection: ISourcesFinancement[] = [sampleWithRequiredData];
        expectedResult = service.addSourcesFinancementToCollectionIfMissing(sourcesFinancementCollection, ...sourcesFinancementArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const sourcesFinancement: ISourcesFinancement = sampleWithRequiredData;
        const sourcesFinancement2: ISourcesFinancement = sampleWithPartialData;
        expectedResult = service.addSourcesFinancementToCollectionIfMissing([], sourcesFinancement, sourcesFinancement2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sourcesFinancement);
        expect(expectedResult).toContain(sourcesFinancement2);
      });

      it('should accept null and undefined values', () => {
        const sourcesFinancement: ISourcesFinancement = sampleWithRequiredData;
        expectedResult = service.addSourcesFinancementToCollectionIfMissing([], null, sourcesFinancement, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sourcesFinancement);
      });

      it('should return initial array if no SourcesFinancement is added', () => {
        const sourcesFinancementCollection: ISourcesFinancement[] = [sampleWithRequiredData];
        expectedResult = service.addSourcesFinancementToCollectionIfMissing(sourcesFinancementCollection, undefined, null);
        expect(expectedResult).toEqual(sourcesFinancementCollection);
      });
    });

    describe('compareSourcesFinancement', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSourcesFinancement(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSourcesFinancement(entity1, entity2);
        const compareResult2 = service.compareSourcesFinancement(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSourcesFinancement(entity1, entity2);
        const compareResult2 = service.compareSourcesFinancement(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSourcesFinancement(entity1, entity2);
        const compareResult2 = service.compareSourcesFinancement(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
