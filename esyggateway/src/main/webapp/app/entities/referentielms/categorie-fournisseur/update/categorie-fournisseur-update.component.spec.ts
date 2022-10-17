import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CategorieFournisseurFormService } from './categorie-fournisseur-form.service';
import { CategorieFournisseurService } from '../service/categorie-fournisseur.service';
import { ICategorieFournisseur } from '../categorie-fournisseur.model';

import { CategorieFournisseurUpdateComponent } from './categorie-fournisseur-update.component';

describe('CategorieFournisseur Management Update Component', () => {
  let comp: CategorieFournisseurUpdateComponent;
  let fixture: ComponentFixture<CategorieFournisseurUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let categorieFournisseurFormService: CategorieFournisseurFormService;
  let categorieFournisseurService: CategorieFournisseurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CategorieFournisseurUpdateComponent],
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
      .overrideTemplate(CategorieFournisseurUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CategorieFournisseurUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    categorieFournisseurFormService = TestBed.inject(CategorieFournisseurFormService);
    categorieFournisseurService = TestBed.inject(CategorieFournisseurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const categorieFournisseur: ICategorieFournisseur = { id: 456 };

      activatedRoute.data = of({ categorieFournisseur });
      comp.ngOnInit();

      expect(comp.categorieFournisseur).toEqual(categorieFournisseur);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategorieFournisseur>>();
      const categorieFournisseur = { id: 123 };
      jest.spyOn(categorieFournisseurFormService, 'getCategorieFournisseur').mockReturnValue(categorieFournisseur);
      jest.spyOn(categorieFournisseurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categorieFournisseur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: categorieFournisseur }));
      saveSubject.complete();

      // THEN
      expect(categorieFournisseurFormService.getCategorieFournisseur).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(categorieFournisseurService.update).toHaveBeenCalledWith(expect.objectContaining(categorieFournisseur));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategorieFournisseur>>();
      const categorieFournisseur = { id: 123 };
      jest.spyOn(categorieFournisseurFormService, 'getCategorieFournisseur').mockReturnValue({ id: null });
      jest.spyOn(categorieFournisseurService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categorieFournisseur: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: categorieFournisseur }));
      saveSubject.complete();

      // THEN
      expect(categorieFournisseurFormService.getCategorieFournisseur).toHaveBeenCalled();
      expect(categorieFournisseurService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategorieFournisseur>>();
      const categorieFournisseur = { id: 123 };
      jest.spyOn(categorieFournisseurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categorieFournisseur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(categorieFournisseurService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
