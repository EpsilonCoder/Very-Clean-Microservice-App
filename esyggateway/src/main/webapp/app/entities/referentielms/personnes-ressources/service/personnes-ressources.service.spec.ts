import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPersonnesRessources } from '../personnes-ressources.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../personnes-ressources.test-samples';

import { PersonnesRessourcesService } from './personnes-ressources.service';

const requireRestSample: IPersonnesRessources = {
  ...sampleWithRequiredData,
};

describe('PersonnesRessources Service', () => {
  let service: PersonnesRessourcesService;
  let httpMock: HttpTestingController;
  let expectedResult: IPersonnesRessources | IPersonnesRessources[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PersonnesRessourcesService);
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

    it('should create a PersonnesRessources', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const personnesRessources = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(personnesRessources).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PersonnesRessources', () => {
      const personnesRessources = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(personnesRessources).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PersonnesRessources', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PersonnesRessources', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PersonnesRessources', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPersonnesRessourcesToCollectionIfMissing', () => {
      it('should add a PersonnesRessources to an empty array', () => {
        const personnesRessources: IPersonnesRessources = sampleWithRequiredData;
        expectedResult = service.addPersonnesRessourcesToCollectionIfMissing([], personnesRessources);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(personnesRessources);
      });

      it('should not add a PersonnesRessources to an array that contains it', () => {
        const personnesRessources: IPersonnesRessources = sampleWithRequiredData;
        const personnesRessourcesCollection: IPersonnesRessources[] = [
          {
            ...personnesRessources,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPersonnesRessourcesToCollectionIfMissing(personnesRessourcesCollection, personnesRessources);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PersonnesRessources to an array that doesn't contain it", () => {
        const personnesRessources: IPersonnesRessources = sampleWithRequiredData;
        const personnesRessourcesCollection: IPersonnesRessources[] = [sampleWithPartialData];
        expectedResult = service.addPersonnesRessourcesToCollectionIfMissing(personnesRessourcesCollection, personnesRessources);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(personnesRessources);
      });

      it('should add only unique PersonnesRessources to an array', () => {
        const personnesRessourcesArray: IPersonnesRessources[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const personnesRessourcesCollection: IPersonnesRessources[] = [sampleWithRequiredData];
        expectedResult = service.addPersonnesRessourcesToCollectionIfMissing(personnesRessourcesCollection, ...personnesRessourcesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const personnesRessources: IPersonnesRessources = sampleWithRequiredData;
        const personnesRessources2: IPersonnesRessources = sampleWithPartialData;
        expectedResult = service.addPersonnesRessourcesToCollectionIfMissing([], personnesRessources, personnesRessources2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(personnesRessources);
        expect(expectedResult).toContain(personnesRessources2);
      });

      it('should accept null and undefined values', () => {
        const personnesRessources: IPersonnesRessources = sampleWithRequiredData;
        expectedResult = service.addPersonnesRessourcesToCollectionIfMissing([], null, personnesRessources, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(personnesRessources);
      });

      it('should return initial array if no PersonnesRessources is added', () => {
        const personnesRessourcesCollection: IPersonnesRessources[] = [sampleWithRequiredData];
        expectedResult = service.addPersonnesRessourcesToCollectionIfMissing(personnesRessourcesCollection, undefined, null);
        expect(expectedResult).toEqual(personnesRessourcesCollection);
      });
    });

    describe('comparePersonnesRessources', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePersonnesRessources(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePersonnesRessources(entity1, entity2);
        const compareResult2 = service.comparePersonnesRessources(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePersonnesRessources(entity1, entity2);
        const compareResult2 = service.comparePersonnesRessources(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePersonnesRessources(entity1, entity2);
        const compareResult2 = service.comparePersonnesRessources(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
