import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IConfigurationTaux } from '../configuration-taux.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../configuration-taux.test-samples';

import { ConfigurationTauxService, RestConfigurationTaux } from './configuration-taux.service';

const requireRestSample: RestConfigurationTaux = {
  ...sampleWithRequiredData,
  dateDebut: sampleWithRequiredData.dateDebut?.toJSON(),
  dateFin: sampleWithRequiredData.dateFin?.toJSON(),
};

describe('ConfigurationTaux Service', () => {
  let service: ConfigurationTauxService;
  let httpMock: HttpTestingController;
  let expectedResult: IConfigurationTaux | IConfigurationTaux[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ConfigurationTauxService);
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

    it('should create a ConfigurationTaux', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const configurationTaux = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(configurationTaux).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ConfigurationTaux', () => {
      const configurationTaux = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(configurationTaux).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ConfigurationTaux', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ConfigurationTaux', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ConfigurationTaux', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addConfigurationTauxToCollectionIfMissing', () => {
      it('should add a ConfigurationTaux to an empty array', () => {
        const configurationTaux: IConfigurationTaux = sampleWithRequiredData;
        expectedResult = service.addConfigurationTauxToCollectionIfMissing([], configurationTaux);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(configurationTaux);
      });

      it('should not add a ConfigurationTaux to an array that contains it', () => {
        const configurationTaux: IConfigurationTaux = sampleWithRequiredData;
        const configurationTauxCollection: IConfigurationTaux[] = [
          {
            ...configurationTaux,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addConfigurationTauxToCollectionIfMissing(configurationTauxCollection, configurationTaux);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ConfigurationTaux to an array that doesn't contain it", () => {
        const configurationTaux: IConfigurationTaux = sampleWithRequiredData;
        const configurationTauxCollection: IConfigurationTaux[] = [sampleWithPartialData];
        expectedResult = service.addConfigurationTauxToCollectionIfMissing(configurationTauxCollection, configurationTaux);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(configurationTaux);
      });

      it('should add only unique ConfigurationTaux to an array', () => {
        const configurationTauxArray: IConfigurationTaux[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const configurationTauxCollection: IConfigurationTaux[] = [sampleWithRequiredData];
        expectedResult = service.addConfigurationTauxToCollectionIfMissing(configurationTauxCollection, ...configurationTauxArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const configurationTaux: IConfigurationTaux = sampleWithRequiredData;
        const configurationTaux2: IConfigurationTaux = sampleWithPartialData;
        expectedResult = service.addConfigurationTauxToCollectionIfMissing([], configurationTaux, configurationTaux2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(configurationTaux);
        expect(expectedResult).toContain(configurationTaux2);
      });

      it('should accept null and undefined values', () => {
        const configurationTaux: IConfigurationTaux = sampleWithRequiredData;
        expectedResult = service.addConfigurationTauxToCollectionIfMissing([], null, configurationTaux, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(configurationTaux);
      });

      it('should return initial array if no ConfigurationTaux is added', () => {
        const configurationTauxCollection: IConfigurationTaux[] = [sampleWithRequiredData];
        expectedResult = service.addConfigurationTauxToCollectionIfMissing(configurationTauxCollection, undefined, null);
        expect(expectedResult).toEqual(configurationTauxCollection);
      });
    });

    describe('compareConfigurationTaux', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareConfigurationTaux(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareConfigurationTaux(entity1, entity2);
        const compareResult2 = service.compareConfigurationTaux(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareConfigurationTaux(entity1, entity2);
        const compareResult2 = service.compareConfigurationTaux(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareConfigurationTaux(entity1, entity2);
        const compareResult2 = service.compareConfigurationTaux(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
