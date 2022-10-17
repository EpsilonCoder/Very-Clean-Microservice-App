import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FournisseurFormService } from './fournisseur-form.service';
import { FournisseurService } from '../service/fournisseur.service';
import { IFournisseur } from '../fournisseur.model';
import { ICategorieFournisseur } from 'app/entities/referentielms/categorie-fournisseur/categorie-fournisseur.model';
import { CategorieFournisseurService } from 'app/entities/referentielms/categorie-fournisseur/service/categorie-fournisseur.service';
import { IPays } from 'app/entities/referentielms/pays/pays.model';
import { PaysService } from 'app/entities/referentielms/pays/service/pays.service';

import { FournisseurUpdateComponent } from './fournisseur-update.component';

describe('Fournisseur Management Update Component', () => {
  let comp: FournisseurUpdateComponent;
  let fixture: ComponentFixture<FournisseurUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fournisseurFormService: FournisseurFormService;
  let fournisseurService: FournisseurService;
  let categorieFournisseurService: CategorieFournisseurService;
  let paysService: PaysService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FournisseurUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(FournisseurUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FournisseurUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fournisseurFormService = TestBed.inject(FournisseurFormService);
    fournisseurService = TestBed.inject(FournisseurService);
    categorieFournisseurService = TestBed.inject(CategorieFournisseurService);
    paysService = TestBed.inject(PaysService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CategorieFournisseur query and add missing value', () => {
      const fournisseur: IFournisseur = { id: 456 };
      const categorieFournisseur: ICategorieFournisseur = { id: 60123 };
      fournisseur.categorieFournisseur = categorieFournisseur;

      const categorieFournisseurCollection: ICategorieFournisseur[] = [{ id: 26025 }];
      jest.spyOn(categorieFournisseurService, 'query').mockReturnValue(of(new HttpResponse({ body: categorieFournisseurCollection })));
      const additionalCategorieFournisseurs = [categorieFournisseur];
      const expectedCollection: ICategorieFournisseur[] = [...additionalCategorieFournisseurs, ...categorieFournisseurCollection];
      jest.spyOn(categorieFournisseurService, 'addCategorieFournisseurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fournisseur });
      comp.ngOnInit();

      expect(categorieFournisseurService.query).toHaveBeenCalled();
      expect(categorieFournisseurService.addCategorieFournisseurToCollectionIfMissing).toHaveBeenCalledWith(
        categorieFournisseurCollection,
        ...additionalCategorieFournisseurs.map(expect.objectContaining)
      );
      expect(comp.categorieFournisseursSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Pays query and add missing value', () => {
      const fournisseur: IFournisseur = { id: 456 };
      const pays: IPays = { id: 42456 };
      fournisseur.pays = pays;

      const paysCollection: IPays[] = [{ id: 88508 }];
      jest.spyOn(paysService, 'query').mockReturnValue(of(new HttpResponse({ body: paysCollection })));
      const additionalPays = [pays];
      const expectedCollection: IPays[] = [...additionalPays, ...paysCollection];
      jest.spyOn(paysService, 'addPaysToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fournisseur });
      comp.ngOnInit();

      expect(paysService.query).toHaveBeenCalled();
      expect(paysService.addPaysToCollectionIfMissing).toHaveBeenCalledWith(paysCollection, ...additionalPays.map(expect.objectContaining));
      expect(comp.paysSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const fournisseur: IFournisseur = { id: 456 };
      const categorieFournisseur: ICategorieFournisseur = { id: 58567 };
      fournisseur.categorieFournisseur = categorieFournisseur;
      const pays: IPays = { id: 15134 };
      fournisseur.pays = pays;

      activatedRoute.data = of({ fournisseur });
      comp.ngOnInit();

      expect(comp.categorieFournisseursSharedCollection).toContain(categorieFournisseur);
      expect(comp.paysSharedCollection).toContain(pays);
      expect(comp.fournisseur).toEqual(fournisseur);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFournisseur>>();
      const fournisseur = { id: 123 };
      jest.spyOn(fournisseurFormService, 'getFournisseur').mockReturnValue(fournisseur);
      jest.spyOn(fournisseurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fournisseur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fournisseur }));
      saveSubject.complete();

      // THEN
      expect(fournisseurFormService.getFournisseur).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fournisseurService.update).toHaveBeenCalledWith(expect.objectContaining(fournisseur));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFournisseur>>();
      const fournisseur = { id: 123 };
      jest.spyOn(fournisseurFormService, 'getFournisseur').mockReturnValue({ id: null });
      jest.spyOn(fournisseurService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fournisseur: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fournisseur }));
      saveSubject.complete();

      // THEN
      expect(fournisseurFormService.getFournisseur).toHaveBeenCalled();
      expect(fournisseurService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFournisseur>>();
      const fournisseur = { id: 123 };
      jest.spyOn(fournisseurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fournisseur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fournisseurService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCategorieFournisseur', () => {
      it('Should forward to categorieFournisseurService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(categorieFournisseurService, 'compareCategorieFournisseur');
        comp.compareCategorieFournisseur(entity, entity2);
        expect(categorieFournisseurService.compareCategorieFournisseur).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePays', () => {
      it('Should forward to paysService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(paysService, 'comparePays');
        comp.comparePays(entity, entity2);
        expect(paysService.comparePays).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
