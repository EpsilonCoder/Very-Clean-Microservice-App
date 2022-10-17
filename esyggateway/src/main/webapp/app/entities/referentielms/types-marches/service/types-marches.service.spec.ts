import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITypesMarches } from '../types-marches.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../types-marches.test-samples';

import { TypesMarchesService } from './types-marches.service';

const requireRestSample: ITypesMarches = {
  ...sampleWithRequiredData,
};

describe('TypesMarches Service', () => {
  let service: TypesMarchesService;
  let httpMock: HttpTestingController;
  let expectedResult: ITypesMarches | ITypesMarches[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TypesMarchesService);
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

    it('should create a TypesMarches', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const typesMarches = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(typesMarches).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TypesMarches', () => {
      const typesMarches = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(typesMarches).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TypesMarches', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TypesMarches', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TypesMarches', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTypesMarchesToCollectionIfMissing', () => {
      it('should add a TypesMarches to an empty array', () => {
        const typesMarches: ITypesMarches = sampleWithRequiredData;
        expectedResult = service.addTypesMarchesToCollectionIfMissing([], typesMarches);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(typesMarches);
      });

      it('should not add a TypesMarches to an array that contains it', () => {
        const typesMarches: ITypesMarches = sampleWithRequiredData;
        const typesMarchesCollection: ITypesMarches[] = [
          {
            ...typesMarches,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTypesMarchesToCollectionIfMissing(typesMarchesCollection, typesMarches);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TypesMarches to an array that doesn't contain it", () => {
        const typesMarches: ITypesMarches = sampleWithRequiredData;
        const typesMarchesCollection: ITypesMarches[] = [sampleWithPartialData];
        expectedResult = service.addTypesMarchesToCollectionIfMissing(typesMarchesCollection, typesMarches);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(typesMarches);
      });

      it('should add only unique TypesMarches to an array', () => {
        const typesMarchesArray: ITypesMarches[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const typesMarchesCollection: ITypesMarches[] = [sampleWithRequiredData];
        expectedResult = service.addTypesMarchesToCollectionIfMissing(typesMarchesCollection, ...typesMarchesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const typesMarches: ITypesMarches = sampleWithRequiredData;
        const typesMarches2: ITypesMarches = sampleWithPartialData;
        expectedResult = service.addTypesMarchesToCollectionIfMissing([], typesMarches, typesMarches2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(typesMarches);
        expect(expectedResult).toContain(typesMarches2);
      });

      it('should accept null and undefined values', () => {
        const typesMarches: ITypesMarches = sampleWithRequiredData;
        expectedResult = service.addTypesMarchesToCollectionIfMissing([], null, typesMarches, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(typesMarches);
      });

      it('should return initial array if no TypesMarches is added', () => {
        const typesMarchesCollection: ITypesMarches[] = [sampleWithRequiredData];
        expectedResult = service.addTypesMarchesToCollectionIfMissing(typesMarchesCollection, undefined, null);
        expect(expectedResult).toEqual(typesMarchesCollection);
      });
    });

    describe('compareTypesMarches', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTypesMarches(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTypesMarches(entity1, entity2);
        const compareResult2 = service.compareTypesMarches(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTypesMarches(entity1, entity2);
        const compareResult2 = service.compareTypesMarches(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTypesMarches(entity1, entity2);
        const compareResult2 = service.compareTypesMarches(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
