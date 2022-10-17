import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISpecialitesPersonnel } from '../specialites-personnel.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../specialites-personnel.test-samples';

import { SpecialitesPersonnelService } from './specialites-personnel.service';

const requireRestSample: ISpecialitesPersonnel = {
  ...sampleWithRequiredData,
};

describe('SpecialitesPersonnel Service', () => {
  let service: SpecialitesPersonnelService;
  let httpMock: HttpTestingController;
  let expectedResult: ISpecialitesPersonnel | ISpecialitesPersonnel[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SpecialitesPersonnelService);
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

    it('should create a SpecialitesPersonnel', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const specialitesPersonnel = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(specialitesPersonnel).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SpecialitesPersonnel', () => {
      const specialitesPersonnel = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(specialitesPersonnel).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SpecialitesPersonnel', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SpecialitesPersonnel', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SpecialitesPersonnel', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSpecialitesPersonnelToCollectionIfMissing', () => {
      it('should add a SpecialitesPersonnel to an empty array', () => {
        const specialitesPersonnel: ISpecialitesPersonnel = sampleWithRequiredData;
        expectedResult = service.addSpecialitesPersonnelToCollectionIfMissing([], specialitesPersonnel);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(specialitesPersonnel);
      });

      it('should not add a SpecialitesPersonnel to an array that contains it', () => {
        const specialitesPersonnel: ISpecialitesPersonnel = sampleWithRequiredData;
        const specialitesPersonnelCollection: ISpecialitesPersonnel[] = [
          {
            ...specialitesPersonnel,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSpecialitesPersonnelToCollectionIfMissing(specialitesPersonnelCollection, specialitesPersonnel);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SpecialitesPersonnel to an array that doesn't contain it", () => {
        const specialitesPersonnel: ISpecialitesPersonnel = sampleWithRequiredData;
        const specialitesPersonnelCollection: ISpecialitesPersonnel[] = [sampleWithPartialData];
        expectedResult = service.addSpecialitesPersonnelToCollectionIfMissing(specialitesPersonnelCollection, specialitesPersonnel);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(specialitesPersonnel);
      });

      it('should add only unique SpecialitesPersonnel to an array', () => {
        const specialitesPersonnelArray: ISpecialitesPersonnel[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const specialitesPersonnelCollection: ISpecialitesPersonnel[] = [sampleWithRequiredData];
        expectedResult = service.addSpecialitesPersonnelToCollectionIfMissing(specialitesPersonnelCollection, ...specialitesPersonnelArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const specialitesPersonnel: ISpecialitesPersonnel = sampleWithRequiredData;
        const specialitesPersonnel2: ISpecialitesPersonnel = sampleWithPartialData;
        expectedResult = service.addSpecialitesPersonnelToCollectionIfMissing([], specialitesPersonnel, specialitesPersonnel2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(specialitesPersonnel);
        expect(expectedResult).toContain(specialitesPersonnel2);
      });

      it('should accept null and undefined values', () => {
        const specialitesPersonnel: ISpecialitesPersonnel = sampleWithRequiredData;
        expectedResult = service.addSpecialitesPersonnelToCollectionIfMissing([], null, specialitesPersonnel, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(specialitesPersonnel);
      });

      it('should return initial array if no SpecialitesPersonnel is added', () => {
        const specialitesPersonnelCollection: ISpecialitesPersonnel[] = [sampleWithRequiredData];
        expectedResult = service.addSpecialitesPersonnelToCollectionIfMissing(specialitesPersonnelCollection, undefined, null);
        expect(expectedResult).toEqual(specialitesPersonnelCollection);
      });
    });

    describe('compareSpecialitesPersonnel', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSpecialitesPersonnel(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSpecialitesPersonnel(entity1, entity2);
        const compareResult2 = service.compareSpecialitesPersonnel(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSpecialitesPersonnel(entity1, entity2);
        const compareResult2 = service.compareSpecialitesPersonnel(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSpecialitesPersonnel(entity1, entity2);
        const compareResult2 = service.compareSpecialitesPersonnel(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
