import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DelaisFormService } from './delais-form.service';
import { DelaisService } from '../service/delais.service';
import { IDelais } from '../delais.model';

import { DelaisUpdateComponent } from './delais-update.component';

describe('Delais Management Update Component', () => {
  let comp: DelaisUpdateComponent;
  let fixture: ComponentFixture<DelaisUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let delaisFormService: DelaisFormService;
  let delaisService: DelaisService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DelaisUpdateComponent],
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
      .overrideTemplate(DelaisUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DelaisUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    delaisFormService = TestBed.inject(DelaisFormService);
    delaisService = TestBed.inject(DelaisService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const delais: IDelais = { id: 456 };

      activatedRoute.data = of({ delais });
      comp.ngOnInit();

      expect(comp.delais).toEqual(delais);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDelais>>();
      const delais = { id: 123 };
      jest.spyOn(delaisFormService, 'getDelais').mockReturnValue(delais);
      jest.spyOn(delaisService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ delais });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: delais }));
      saveSubject.complete();

      // THEN
      expect(delaisFormService.getDelais).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(delaisService.update).toHaveBeenCalledWith(expect.objectContaining(delais));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDelais>>();
      const delais = { id: 123 };
      jest.spyOn(delaisFormService, 'getDelais').mockReturnValue({ id: null });
      jest.spyOn(delaisService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ delais: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: delais }));
      saveSubject.complete();

      // THEN
      expect(delaisFormService.getDelais).toHaveBeenCalled();
      expect(delaisService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDelais>>();
      const delais = { id: 123 };
      jest.spyOn(delaisService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ delais });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(delaisService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
