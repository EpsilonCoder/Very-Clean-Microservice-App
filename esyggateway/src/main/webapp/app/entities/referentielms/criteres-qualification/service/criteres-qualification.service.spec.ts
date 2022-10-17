import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICriteresQualification } from '../criteres-qualification.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../criteres-qualification.test-samples';

import { CriteresQualificationService } from './criteres-qualification.service';

const requireRestSample: ICriteresQualification = {
  ...sampleWithRequiredData,
};

describe('CriteresQualification Service', () => {
  let service: CriteresQualificationService;
  let httpMock: HttpTestingController;
  let expectedResult: ICriteresQualification | ICriteresQualification[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CriteresQualificationService);
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

    it('should create a CriteresQualification', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const criteresQualification = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(criteresQualification).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CriteresQualification', () => {
      const criteresQualification = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(criteresQualification).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CriteresQualification', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CriteresQualification', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CriteresQualification', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCriteresQualificationToCollectionIfMissing', () => {
      it('should add a CriteresQualification to an empty array', () => {
        const criteresQualification: ICriteresQualification = sampleWithRequiredData;
        expectedResult = service.addCriteresQualificationToCollectionIfMissing([], criteresQualification);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(criteresQualification);
      });

      it('should not add a CriteresQualification to an array that contains it', () => {
        const criteresQualification: ICriteresQualification = sampleWithRequiredData;
        const criteresQualificationCollection: ICriteresQualification[] = [
          {
            ...criteresQualification,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCriteresQualificationToCollectionIfMissing(criteresQualificationCollection, criteresQualification);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CriteresQualification to an array that doesn't contain it", () => {
        const criteresQualification: ICriteresQualification = sampleWithRequiredData;
        const criteresQualificationCollection: ICriteresQualification[] = [sampleWithPartialData];
        expectedResult = service.addCriteresQualificationToCollectionIfMissing(criteresQualificationCollection, criteresQualification);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(criteresQualification);
      });

      it('should add only unique CriteresQualification to an array', () => {
        const criteresQualificationArray: ICriteresQualification[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const criteresQualificationCollection: ICriteresQualification[] = [sampleWithRequiredData];
        expectedResult = service.addCriteresQualificationToCollectionIfMissing(
          criteresQualificationCollection,
          ...criteresQualificationArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const criteresQualification: ICriteresQualification = sampleWithRequiredData;
        const criteresQualification2: ICriteresQualification = sampleWithPartialData;
        expectedResult = service.addCriteresQualificationToCollectionIfMissing([], criteresQualification, criteresQualification2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(criteresQualification);
        expect(expectedResult).toContain(criteresQualification2);
      });

      it('should accept null and undefined values', () => {
        const criteresQualification: ICriteresQualification = sampleWithRequiredData;
        expectedResult = service.addCriteresQualificationToCollectionIfMissing([], null, criteresQualification, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(criteresQualification);
      });

      it('should return initial array if no CriteresQualification is added', () => {
        const criteresQualificationCollection: ICriteresQualification[] = [sampleWithRequiredData];
        expectedResult = service.addCriteresQualificationToCollectionIfMissing(criteresQualificationCollection, undefined, null);
        expect(expectedResult).toEqual(criteresQualificationCollection);
      });
    });

    describe('compareCriteresQualification', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCriteresQualification(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCriteresQualification(entity1, entity2);
        const compareResult2 = service.compareCriteresQualification(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCriteresQualification(entity1, entity2);
        const compareResult2 = service.compareCriteresQualification(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCriteresQualification(entity1, entity2);
        const compareResult2 = service.compareCriteresQualification(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
