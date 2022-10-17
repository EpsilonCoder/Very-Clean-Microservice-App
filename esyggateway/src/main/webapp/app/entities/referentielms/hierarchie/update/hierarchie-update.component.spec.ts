import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { HierarchieFormService } from './hierarchie-form.service';
import { HierarchieService } from '../service/hierarchie.service';
import { IHierarchie } from '../hierarchie.model';

import { HierarchieUpdateComponent } from './hierarchie-update.component';

describe('Hierarchie Management Update Component', () => {
  let comp: HierarchieUpdateComponent;
  let fixture: ComponentFixture<HierarchieUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let hierarchieFormService: HierarchieFormService;
  let hierarchieService: HierarchieService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [HierarchieUpdateComponent],
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
      .overrideTemplate(HierarchieUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HierarchieUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    hierarchieFormService = TestBed.inject(HierarchieFormService);
    hierarchieService = TestBed.inject(HierarchieService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const hierarchie: IHierarchie = { id: 456 };

      activatedRoute.data = of({ hierarchie });
      comp.ngOnInit();

      expect(comp.hierarchie).toEqual(hierarchie);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHierarchie>>();
      const hierarchie = { id: 123 };
      jest.spyOn(hierarchieFormService, 'getHierarchie').mockReturnValue(hierarchie);
      jest.spyOn(hierarchieService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hierarchie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hierarchie }));
      saveSubject.complete();

      // THEN
      expect(hierarchieFormService.getHierarchie).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(hierarchieService.update).toHaveBeenCalledWith(expect.objectContaining(hierarchie));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHierarchie>>();
      const hierarchie = { id: 123 };
      jest.spyOn(hierarchieFormService, 'getHierarchie').mockReturnValue({ id: null });
      jest.spyOn(hierarchieService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hierarchie: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hierarchie }));
      saveSubject.complete();

      // THEN
      expect(hierarchieFormService.getHierarchie).toHaveBeenCalled();
      expect(hierarchieService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHierarchie>>();
      const hierarchie = { id: 123 };
      jest.spyOn(hierarchieService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hierarchie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(hierarchieService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
