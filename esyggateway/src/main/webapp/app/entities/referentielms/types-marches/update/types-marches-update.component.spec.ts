import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TypesMarchesFormService } from './types-marches-form.service';
import { TypesMarchesService } from '../service/types-marches.service';
import { ITypesMarches } from '../types-marches.model';

import { TypesMarchesUpdateComponent } from './types-marches-update.component';

describe('TypesMarches Management Update Component', () => {
  let comp: TypesMarchesUpdateComponent;
  let fixture: ComponentFixture<TypesMarchesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let typesMarchesFormService: TypesMarchesFormService;
  let typesMarchesService: TypesMarchesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TypesMarchesUpdateComponent],
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
      .overrideTemplate(TypesMarchesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TypesMarchesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    typesMarchesFormService = TestBed.inject(TypesMarchesFormService);
    typesMarchesService = TestBed.inject(TypesMarchesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const typesMarches: ITypesMarches = { id: 456 };

      activatedRoute.data = of({ typesMarches });
      comp.ngOnInit();

      expect(comp.typesMarches).toEqual(typesMarches);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITypesMarches>>();
      const typesMarches = { id: 123 };
      jest.spyOn(typesMarchesFormService, 'getTypesMarches').mockReturnValue(typesMarches);
      jest.spyOn(typesMarchesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typesMarches });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: typesMarches }));
      saveSubject.complete();

      // THEN
      expect(typesMarchesFormService.getTypesMarches).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(typesMarchesService.update).toHaveBeenCalledWith(expect.objectContaining(typesMarches));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITypesMarches>>();
      const typesMarches = { id: 123 };
      jest.spyOn(typesMarchesFormService, 'getTypesMarches').mockReturnValue({ id: null });
      jest.spyOn(typesMarchesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typesMarches: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: typesMarches }));
      saveSubject.complete();

      // THEN
      expect(typesMarchesFormService.getTypesMarches).toHaveBeenCalled();
      expect(typesMarchesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITypesMarches>>();
      const typesMarches = { id: 123 };
      jest.spyOn(typesMarchesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typesMarches });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(typesMarchesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
