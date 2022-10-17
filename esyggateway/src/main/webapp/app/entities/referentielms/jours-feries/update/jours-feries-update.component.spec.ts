import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { JoursFeriesFormService } from './jours-feries-form.service';
import { JoursFeriesService } from '../service/jours-feries.service';
import { IJoursFeries } from '../jours-feries.model';

import { JoursFeriesUpdateComponent } from './jours-feries-update.component';

describe('JoursFeries Management Update Component', () => {
  let comp: JoursFeriesUpdateComponent;
  let fixture: ComponentFixture<JoursFeriesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let joursFeriesFormService: JoursFeriesFormService;
  let joursFeriesService: JoursFeriesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [JoursFeriesUpdateComponent],
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
      .overrideTemplate(JoursFeriesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(JoursFeriesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    joursFeriesFormService = TestBed.inject(JoursFeriesFormService);
    joursFeriesService = TestBed.inject(JoursFeriesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const joursFeries: IJoursFeries = { id: 456 };

      activatedRoute.data = of({ joursFeries });
      comp.ngOnInit();

      expect(comp.joursFeries).toEqual(joursFeries);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJoursFeries>>();
      const joursFeries = { id: 123 };
      jest.spyOn(joursFeriesFormService, 'getJoursFeries').mockReturnValue(joursFeries);
      jest.spyOn(joursFeriesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ joursFeries });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: joursFeries }));
      saveSubject.complete();

      // THEN
      expect(joursFeriesFormService.getJoursFeries).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(joursFeriesService.update).toHaveBeenCalledWith(expect.objectContaining(joursFeries));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJoursFeries>>();
      const joursFeries = { id: 123 };
      jest.spyOn(joursFeriesFormService, 'getJoursFeries').mockReturnValue({ id: null });
      jest.spyOn(joursFeriesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ joursFeries: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: joursFeries }));
      saveSubject.complete();

      // THEN
      expect(joursFeriesFormService.getJoursFeries).toHaveBeenCalled();
      expect(joursFeriesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJoursFeries>>();
      const joursFeries = { id: 123 };
      jest.spyOn(joursFeriesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ joursFeries });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(joursFeriesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
