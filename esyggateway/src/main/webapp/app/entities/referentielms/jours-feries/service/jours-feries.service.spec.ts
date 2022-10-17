import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IJoursFeries } from '../jours-feries.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../jours-feries.test-samples';

import { JoursFeriesService, RestJoursFeries } from './jours-feries.service';

const requireRestSample: RestJoursFeries = {
  ...sampleWithRequiredData,
  date: sampleWithRequiredData.date?.toJSON(),
};

describe('JoursFeries Service', () => {
  let service: JoursFeriesService;
  let httpMock: HttpTestingController;
  let expectedResult: IJoursFeries | IJoursFeries[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(JoursFeriesService);
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

    it('should create a JoursFeries', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const joursFeries = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(joursFeries).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a JoursFeries', () => {
      const joursFeries = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(joursFeries).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a JoursFeries', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of JoursFeries', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a JoursFeries', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addJoursFeriesToCollectionIfMissing', () => {
      it('should add a JoursFeries to an empty array', () => {
        const joursFeries: IJoursFeries = sampleWithRequiredData;
        expectedResult = service.addJoursFeriesToCollectionIfMissing([], joursFeries);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(joursFeries);
      });

      it('should not add a JoursFeries to an array that contains it', () => {
        const joursFeries: IJoursFeries = sampleWithRequiredData;
        const joursFeriesCollection: IJoursFeries[] = [
          {
            ...joursFeries,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addJoursFeriesToCollectionIfMissing(joursFeriesCollection, joursFeries);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a JoursFeries to an array that doesn't contain it", () => {
        const joursFeries: IJoursFeries = sampleWithRequiredData;
        const joursFeriesCollection: IJoursFeries[] = [sampleWithPartialData];
        expectedResult = service.addJoursFeriesToCollectionIfMissing(joursFeriesCollection, joursFeries);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(joursFeries);
      });

      it('should add only unique JoursFeries to an array', () => {
        const joursFeriesArray: IJoursFeries[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const joursFeriesCollection: IJoursFeries[] = [sampleWithRequiredData];
        expectedResult = service.addJoursFeriesToCollectionIfMissing(joursFeriesCollection, ...joursFeriesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const joursFeries: IJoursFeries = sampleWithRequiredData;
        const joursFeries2: IJoursFeries = sampleWithPartialData;
        expectedResult = service.addJoursFeriesToCollectionIfMissing([], joursFeries, joursFeries2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(joursFeries);
        expect(expectedResult).toContain(joursFeries2);
      });

      it('should accept null and undefined values', () => {
        const joursFeries: IJoursFeries = sampleWithRequiredData;
        expectedResult = service.addJoursFeriesToCollectionIfMissing([], null, joursFeries, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(joursFeries);
      });

      it('should return initial array if no JoursFeries is added', () => {
        const joursFeriesCollection: IJoursFeries[] = [sampleWithRequiredData];
        expectedResult = service.addJoursFeriesToCollectionIfMissing(joursFeriesCollection, undefined, null);
        expect(expectedResult).toEqual(joursFeriesCollection);
      });
    });

    describe('compareJoursFeries', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareJoursFeries(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareJoursFeries(entity1, entity2);
        const compareResult2 = service.compareJoursFeries(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareJoursFeries(entity1, entity2);
        const compareResult2 = service.compareJoursFeries(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareJoursFeries(entity1, entity2);
        const compareResult2 = service.compareJoursFeries(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
