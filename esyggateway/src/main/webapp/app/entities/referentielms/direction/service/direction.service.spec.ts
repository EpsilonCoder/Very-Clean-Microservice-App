import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDirection } from '../direction.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../direction.test-samples';

import { DirectionService } from './direction.service';

const requireRestSample: IDirection = {
  ...sampleWithRequiredData,
};

describe('Direction Service', () => {
  let service: DirectionService;
  let httpMock: HttpTestingController;
  let expectedResult: IDirection | IDirection[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DirectionService);
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

    it('should create a Direction', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const direction = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(direction).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Direction', () => {
      const direction = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(direction).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Direction', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Direction', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Direction', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDirectionToCollectionIfMissing', () => {
      it('should add a Direction to an empty array', () => {
        const direction: IDirection = sampleWithRequiredData;
        expectedResult = service.addDirectionToCollectionIfMissing([], direction);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(direction);
      });

      it('should not add a Direction to an array that contains it', () => {
        const direction: IDirection = sampleWithRequiredData;
        const directionCollection: IDirection[] = [
          {
            ...direction,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDirectionToCollectionIfMissing(directionCollection, direction);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Direction to an array that doesn't contain it", () => {
        const direction: IDirection = sampleWithRequiredData;
        const directionCollection: IDirection[] = [sampleWithPartialData];
        expectedResult = service.addDirectionToCollectionIfMissing(directionCollection, direction);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(direction);
      });

      it('should add only unique Direction to an array', () => {
        const directionArray: IDirection[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const directionCollection: IDirection[] = [sampleWithRequiredData];
        expectedResult = service.addDirectionToCollectionIfMissing(directionCollection, ...directionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const direction: IDirection = sampleWithRequiredData;
        const direction2: IDirection = sampleWithPartialData;
        expectedResult = service.addDirectionToCollectionIfMissing([], direction, direction2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(direction);
        expect(expectedResult).toContain(direction2);
      });

      it('should accept null and undefined values', () => {
        const direction: IDirection = sampleWithRequiredData;
        expectedResult = service.addDirectionToCollectionIfMissing([], null, direction, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(direction);
      });

      it('should return initial array if no Direction is added', () => {
        const directionCollection: IDirection[] = [sampleWithRequiredData];
        expectedResult = service.addDirectionToCollectionIfMissing(directionCollection, undefined, null);
        expect(expectedResult).toEqual(directionCollection);
      });
    });

    describe('compareDirection', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDirection(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDirection(entity1, entity2);
        const compareResult2 = service.compareDirection(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDirection(entity1, entity2);
        const compareResult2 = service.compareDirection(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDirection(entity1, entity2);
        const compareResult2 = service.compareDirection(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
