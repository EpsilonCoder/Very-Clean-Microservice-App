import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IGroupesImputation } from '../groupes-imputation.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../groupes-imputation.test-samples';

import { GroupesImputationService } from './groupes-imputation.service';

const requireRestSample: IGroupesImputation = {
  ...sampleWithRequiredData,
};

describe('GroupesImputation Service', () => {
  let service: GroupesImputationService;
  let httpMock: HttpTestingController;
  let expectedResult: IGroupesImputation | IGroupesImputation[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(GroupesImputationService);
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

    it('should create a GroupesImputation', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const groupesImputation = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(groupesImputation).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a GroupesImputation', () => {
      const groupesImputation = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(groupesImputation).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a GroupesImputation', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of GroupesImputation', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a GroupesImputation', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addGroupesImputationToCollectionIfMissing', () => {
      it('should add a GroupesImputation to an empty array', () => {
        const groupesImputation: IGroupesImputation = sampleWithRequiredData;
        expectedResult = service.addGroupesImputationToCollectionIfMissing([], groupesImputation);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(groupesImputation);
      });

      it('should not add a GroupesImputation to an array that contains it', () => {
        const groupesImputation: IGroupesImputation = sampleWithRequiredData;
        const groupesImputationCollection: IGroupesImputation[] = [
          {
            ...groupesImputation,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addGroupesImputationToCollectionIfMissing(groupesImputationCollection, groupesImputation);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a GroupesImputation to an array that doesn't contain it", () => {
        const groupesImputation: IGroupesImputation = sampleWithRequiredData;
        const groupesImputationCollection: IGroupesImputation[] = [sampleWithPartialData];
        expectedResult = service.addGroupesImputationToCollectionIfMissing(groupesImputationCollection, groupesImputation);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(groupesImputation);
      });

      it('should add only unique GroupesImputation to an array', () => {
        const groupesImputationArray: IGroupesImputation[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const groupesImputationCollection: IGroupesImputation[] = [sampleWithRequiredData];
        expectedResult = service.addGroupesImputationToCollectionIfMissing(groupesImputationCollection, ...groupesImputationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const groupesImputation: IGroupesImputation = sampleWithRequiredData;
        const groupesImputation2: IGroupesImputation = sampleWithPartialData;
        expectedResult = service.addGroupesImputationToCollectionIfMissing([], groupesImputation, groupesImputation2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(groupesImputation);
        expect(expectedResult).toContain(groupesImputation2);
      });

      it('should accept null and undefined values', () => {
        const groupesImputation: IGroupesImputation = sampleWithRequiredData;
        expectedResult = service.addGroupesImputationToCollectionIfMissing([], null, groupesImputation, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(groupesImputation);
      });

      it('should return initial array if no GroupesImputation is added', () => {
        const groupesImputationCollection: IGroupesImputation[] = [sampleWithRequiredData];
        expectedResult = service.addGroupesImputationToCollectionIfMissing(groupesImputationCollection, undefined, null);
        expect(expectedResult).toEqual(groupesImputationCollection);
      });
    });

    describe('compareGroupesImputation', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareGroupesImputation(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareGroupesImputation(entity1, entity2);
        const compareResult2 = service.compareGroupesImputation(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareGroupesImputation(entity1, entity2);
        const compareResult2 = service.compareGroupesImputation(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareGroupesImputation(entity1, entity2);
        const compareResult2 = service.compareGroupesImputation(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
