import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISituationMatrimoniale } from '../situation-matrimoniale.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../situation-matrimoniale.test-samples';

import { SituationMatrimonialeService } from './situation-matrimoniale.service';

const requireRestSample: ISituationMatrimoniale = {
  ...sampleWithRequiredData,
};

describe('SituationMatrimoniale Service', () => {
  let service: SituationMatrimonialeService;
  let httpMock: HttpTestingController;
  let expectedResult: ISituationMatrimoniale | ISituationMatrimoniale[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SituationMatrimonialeService);
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

    it('should create a SituationMatrimoniale', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const situationMatrimoniale = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(situationMatrimoniale).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SituationMatrimoniale', () => {
      const situationMatrimoniale = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(situationMatrimoniale).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SituationMatrimoniale', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SituationMatrimoniale', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SituationMatrimoniale', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSituationMatrimonialeToCollectionIfMissing', () => {
      it('should add a SituationMatrimoniale to an empty array', () => {
        const situationMatrimoniale: ISituationMatrimoniale = sampleWithRequiredData;
        expectedResult = service.addSituationMatrimonialeToCollectionIfMissing([], situationMatrimoniale);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(situationMatrimoniale);
      });

      it('should not add a SituationMatrimoniale to an array that contains it', () => {
        const situationMatrimoniale: ISituationMatrimoniale = sampleWithRequiredData;
        const situationMatrimonialeCollection: ISituationMatrimoniale[] = [
          {
            ...situationMatrimoniale,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSituationMatrimonialeToCollectionIfMissing(situationMatrimonialeCollection, situationMatrimoniale);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SituationMatrimoniale to an array that doesn't contain it", () => {
        const situationMatrimoniale: ISituationMatrimoniale = sampleWithRequiredData;
        const situationMatrimonialeCollection: ISituationMatrimoniale[] = [sampleWithPartialData];
        expectedResult = service.addSituationMatrimonialeToCollectionIfMissing(situationMatrimonialeCollection, situationMatrimoniale);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(situationMatrimoniale);
      });

      it('should add only unique SituationMatrimoniale to an array', () => {
        const situationMatrimonialeArray: ISituationMatrimoniale[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const situationMatrimonialeCollection: ISituationMatrimoniale[] = [sampleWithRequiredData];
        expectedResult = service.addSituationMatrimonialeToCollectionIfMissing(
          situationMatrimonialeCollection,
          ...situationMatrimonialeArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const situationMatrimoniale: ISituationMatrimoniale = sampleWithRequiredData;
        const situationMatrimoniale2: ISituationMatrimoniale = sampleWithPartialData;
        expectedResult = service.addSituationMatrimonialeToCollectionIfMissing([], situationMatrimoniale, situationMatrimoniale2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(situationMatrimoniale);
        expect(expectedResult).toContain(situationMatrimoniale2);
      });

      it('should accept null and undefined values', () => {
        const situationMatrimoniale: ISituationMatrimoniale = sampleWithRequiredData;
        expectedResult = service.addSituationMatrimonialeToCollectionIfMissing([], null, situationMatrimoniale, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(situationMatrimoniale);
      });

      it('should return initial array if no SituationMatrimoniale is added', () => {
        const situationMatrimonialeCollection: ISituationMatrimoniale[] = [sampleWithRequiredData];
        expectedResult = service.addSituationMatrimonialeToCollectionIfMissing(situationMatrimonialeCollection, undefined, null);
        expect(expectedResult).toEqual(situationMatrimonialeCollection);
      });
    });

    describe('compareSituationMatrimoniale', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSituationMatrimoniale(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSituationMatrimoniale(entity1, entity2);
        const compareResult2 = service.compareSituationMatrimoniale(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSituationMatrimoniale(entity1, entity2);
        const compareResult2 = service.compareSituationMatrimoniale(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSituationMatrimoniale(entity1, entity2);
        const compareResult2 = service.compareSituationMatrimoniale(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
