import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SygAutoriteContractanteFormService } from './syg-autorite-contractante-form.service';
import { SygAutoriteContractanteService } from '../service/syg-autorite-contractante.service';
import { ISygAutoriteContractante } from '../syg-autorite-contractante.model';
import { ITypeAutoriteContractante } from 'app/entities/referentielms/type-autorite-contractante/type-autorite-contractante.model';
import { TypeAutoriteContractanteService } from 'app/entities/referentielms/type-autorite-contractante/service/type-autorite-contractante.service';

import { SygAutoriteContractanteUpdateComponent } from './syg-autorite-contractante-update.component';

describe('SygAutoriteContractante Management Update Component', () => {
  let comp: SygAutoriteContractanteUpdateComponent;
  let fixture: ComponentFixture<SygAutoriteContractanteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sygAutoriteContractanteFormService: SygAutoriteContractanteFormService;
  let sygAutoriteContractanteService: SygAutoriteContractanteService;
  let typeAutoriteContractanteService: TypeAutoriteContractanteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SygAutoriteContractanteUpdateComponent],
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
      .overrideTemplate(SygAutoriteContractanteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SygAutoriteContractanteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sygAutoriteContractanteFormService = TestBed.inject(SygAutoriteContractanteFormService);
    sygAutoriteContractanteService = TestBed.inject(SygAutoriteContractanteService);
    typeAutoriteContractanteService = TestBed.inject(TypeAutoriteContractanteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TypeAutoriteContractante query and add missing value', () => {
      const sygAutoriteContractante: ISygAutoriteContractante = { id: 456 };
      const typeAutoriteContractante: ITypeAutoriteContractante = { id: 92231 };
      sygAutoriteContractante.typeAutoriteContractante = typeAutoriteContractante;

      const typeAutoriteContractanteCollection: ITypeAutoriteContractante[] = [{ id: 82944 }];
      jest
        .spyOn(typeAutoriteContractanteService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: typeAutoriteContractanteCollection })));
      const additionalTypeAutoriteContractantes = [typeAutoriteContractante];
      const expectedCollection: ITypeAutoriteContractante[] = [
        ...additionalTypeAutoriteContractantes,
        ...typeAutoriteContractanteCollection,
      ];
      jest.spyOn(typeAutoriteContractanteService, 'addTypeAutoriteContractanteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ sygAutoriteContractante });
      comp.ngOnInit();

      expect(typeAutoriteContractanteService.query).toHaveBeenCalled();
      expect(typeAutoriteContractanteService.addTypeAutoriteContractanteToCollectionIfMissing).toHaveBeenCalledWith(
        typeAutoriteContractanteCollection,
        ...additionalTypeAutoriteContractantes.map(expect.objectContaining)
      );
      expect(comp.typeAutoriteContractantesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const sygAutoriteContractante: ISygAutoriteContractante = { id: 456 };
      const typeAutoriteContractante: ITypeAutoriteContractante = { id: 69266 };
      sygAutoriteContractante.typeAutoriteContractante = typeAutoriteContractante;

      activatedRoute.data = of({ sygAutoriteContractante });
      comp.ngOnInit();

      expect(comp.typeAutoriteContractantesSharedCollection).toContain(typeAutoriteContractante);
      expect(comp.sygAutoriteContractante).toEqual(sygAutoriteContractante);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISygAutoriteContractante>>();
      const sygAutoriteContractante = { id: 123 };
      jest.spyOn(sygAutoriteContractanteFormService, 'getSygAutoriteContractante').mockReturnValue(sygAutoriteContractante);
      jest.spyOn(sygAutoriteContractanteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sygAutoriteContractante });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sygAutoriteContractante }));
      saveSubject.complete();

      // THEN
      expect(sygAutoriteContractanteFormService.getSygAutoriteContractante).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(sygAutoriteContractanteService.update).toHaveBeenCalledWith(expect.objectContaining(sygAutoriteContractante));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISygAutoriteContractante>>();
      const sygAutoriteContractante = { id: 123 };
      jest.spyOn(sygAutoriteContractanteFormService, 'getSygAutoriteContractante').mockReturnValue({ id: null });
      jest.spyOn(sygAutoriteContractanteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sygAutoriteContractante: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sygAutoriteContractante }));
      saveSubject.complete();

      // THEN
      expect(sygAutoriteContractanteFormService.getSygAutoriteContractante).toHaveBeenCalled();
      expect(sygAutoriteContractanteService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISygAutoriteContractante>>();
      const sygAutoriteContractante = { id: 123 };
      jest.spyOn(sygAutoriteContractanteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sygAutoriteContractante });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sygAutoriteContractanteService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTypeAutoriteContractante', () => {
      it('Should forward to typeAutoriteContractanteService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(typeAutoriteContractanteService, 'compareTypeAutoriteContractante');
        comp.compareTypeAutoriteContractante(entity, entity2);
        expect(typeAutoriteContractanteService.compareTypeAutoriteContractante).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
