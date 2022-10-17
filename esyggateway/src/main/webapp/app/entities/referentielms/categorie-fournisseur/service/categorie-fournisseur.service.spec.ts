import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICategorieFournisseur } from '../categorie-fournisseur.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../categorie-fournisseur.test-samples';

import { CategorieFournisseurService } from './categorie-fournisseur.service';

const requireRestSample: ICategorieFournisseur = {
  ...sampleWithRequiredData,
};

describe('CategorieFournisseur Service', () => {
  let service: CategorieFournisseurService;
  let httpMock: HttpTestingController;
  let expectedResult: ICategorieFournisseur | ICategorieFournisseur[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CategorieFournisseurService);
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

    it('should create a CategorieFournisseur', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const categorieFournisseur = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(categorieFournisseur).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CategorieFournisseur', () => {
      const categorieFournisseur = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(categorieFournisseur).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CategorieFournisseur', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CategorieFournisseur', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CategorieFournisseur', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCategorieFournisseurToCollectionIfMissing', () => {
      it('should add a CategorieFournisseur to an empty array', () => {
        const categorieFournisseur: ICategorieFournisseur = sampleWithRequiredData;
        expectedResult = service.addCategorieFournisseurToCollectionIfMissing([], categorieFournisseur);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(categorieFournisseur);
      });

      it('should not add a CategorieFournisseur to an array that contains it', () => {
        const categorieFournisseur: ICategorieFournisseur = sampleWithRequiredData;
        const categorieFournisseurCollection: ICategorieFournisseur[] = [
          {
            ...categorieFournisseur,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCategorieFournisseurToCollectionIfMissing(categorieFournisseurCollection, categorieFournisseur);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CategorieFournisseur to an array that doesn't contain it", () => {
        const categorieFournisseur: ICategorieFournisseur = sampleWithRequiredData;
        const categorieFournisseurCollection: ICategorieFournisseur[] = [sampleWithPartialData];
        expectedResult = service.addCategorieFournisseurToCollectionIfMissing(categorieFournisseurCollection, categorieFournisseur);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(categorieFournisseur);
      });

      it('should add only unique CategorieFournisseur to an array', () => {
        const categorieFournisseurArray: ICategorieFournisseur[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const categorieFournisseurCollection: ICategorieFournisseur[] = [sampleWithRequiredData];
        expectedResult = service.addCategorieFournisseurToCollectionIfMissing(categorieFournisseurCollection, ...categorieFournisseurArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const categorieFournisseur: ICategorieFournisseur = sampleWithRequiredData;
        const categorieFournisseur2: ICategorieFournisseur = sampleWithPartialData;
        expectedResult = service.addCategorieFournisseurToCollectionIfMissing([], categorieFournisseur, categorieFournisseur2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(categorieFournisseur);
        expect(expectedResult).toContain(categorieFournisseur2);
      });

      it('should accept null and undefined values', () => {
        const categorieFournisseur: ICategorieFournisseur = sampleWithRequiredData;
        expectedResult = service.addCategorieFournisseurToCollectionIfMissing([], null, categorieFournisseur, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(categorieFournisseur);
      });

      it('should return initial array if no CategorieFournisseur is added', () => {
        const categorieFournisseurCollection: ICategorieFournisseur[] = [sampleWithRequiredData];
        expectedResult = service.addCategorieFournisseurToCollectionIfMissing(categorieFournisseurCollection, undefined, null);
        expect(expectedResult).toEqual(categorieFournisseurCollection);
      });
    });

    describe('compareCategorieFournisseur', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCategorieFournisseur(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCategorieFournisseur(entity1, entity2);
        const compareResult2 = service.compareCategorieFournisseur(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCategorieFournisseur(entity1, entity2);
        const compareResult2 = service.compareCategorieFournisseur(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCategorieFournisseur(entity1, entity2);
        const compareResult2 = service.compareCategorieFournisseur(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
