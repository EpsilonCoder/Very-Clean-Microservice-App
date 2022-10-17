import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CriteresQualificationFormService } from './criteres-qualification-form.service';
import { CriteresQualificationService } from '../service/criteres-qualification.service';
import { ICriteresQualification } from '../criteres-qualification.model';

import { CriteresQualificationUpdateComponent } from './criteres-qualification-update.component';

describe('CriteresQualification Management Update Component', () => {
  let comp: CriteresQualificationUpdateComponent;
  let fixture: ComponentFixture<CriteresQualificationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let criteresQualificationFormService: CriteresQualificationFormService;
  let criteresQualificationService: CriteresQualificationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CriteresQualificationUpdateComponent],
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
      .overrideTemplate(CriteresQualificationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CriteresQualificationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    criteresQualificationFormService = TestBed.inject(CriteresQualificationFormService);
    criteresQualificationService = TestBed.inject(CriteresQualificationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const criteresQualification: ICriteresQualification = { id: 456 };

      activatedRoute.data = of({ criteresQualification });
      comp.ngOnInit();

      expect(comp.criteresQualification).toEqual(criteresQualification);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICriteresQualification>>();
      const criteresQualification = { id: 123 };
      jest.spyOn(criteresQualificationFormService, 'getCriteresQualification').mockReturnValue(criteresQualification);
      jest.spyOn(criteresQualificationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ criteresQualification });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: criteresQualification }));
      saveSubject.complete();

      // THEN
      expect(criteresQualificationFormService.getCriteresQualification).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(criteresQualificationService.update).toHaveBeenCalledWith(expect.objectContaining(criteresQualification));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICriteresQualification>>();
      const criteresQualification = { id: 123 };
      jest.spyOn(criteresQualificationFormService, 'getCriteresQualification').mockReturnValue({ id: null });
      jest.spyOn(criteresQualificationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ criteresQualification: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: criteresQualification }));
      saveSubject.complete();

      // THEN
      expect(criteresQualificationFormService.getCriteresQualification).toHaveBeenCalled();
      expect(criteresQualificationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICriteresQualification>>();
      const criteresQualification = { id: 123 };
      jest.spyOn(criteresQualificationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ criteresQualification });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(criteresQualificationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
