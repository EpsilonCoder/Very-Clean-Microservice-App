import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AvisGenerauxFormService } from './avis-generaux-form.service';
import { AvisGenerauxService } from '../service/avis-generaux.service';
import { IAvisGeneraux } from '../avis-generaux.model';

import { AvisGenerauxUpdateComponent } from './avis-generaux-update.component';

describe('AvisGeneraux Management Update Component', () => {
  let comp: AvisGenerauxUpdateComponent;
  let fixture: ComponentFixture<AvisGenerauxUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let avisGenerauxFormService: AvisGenerauxFormService;
  let avisGenerauxService: AvisGenerauxService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AvisGenerauxUpdateComponent],
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
      .overrideTemplate(AvisGenerauxUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AvisGenerauxUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    avisGenerauxFormService = TestBed.inject(AvisGenerauxFormService);
    avisGenerauxService = TestBed.inject(AvisGenerauxService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const avisGeneraux: IAvisGeneraux = { id: 456 };

      activatedRoute.data = of({ avisGeneraux });
      comp.ngOnInit();

      expect(comp.avisGeneraux).toEqual(avisGeneraux);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAvisGeneraux>>();
      const avisGeneraux = { id: 123 };
      jest.spyOn(avisGenerauxFormService, 'getAvisGeneraux').mockReturnValue(avisGeneraux);
      jest.spyOn(avisGenerauxService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ avisGeneraux });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: avisGeneraux }));
      saveSubject.complete();

      // THEN
      expect(avisGenerauxFormService.getAvisGeneraux).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(avisGenerauxService.update).toHaveBeenCalledWith(expect.objectContaining(avisGeneraux));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAvisGeneraux>>();
      const avisGeneraux = { id: 123 };
      jest.spyOn(avisGenerauxFormService, 'getAvisGeneraux').mockReturnValue({ id: null });
      jest.spyOn(avisGenerauxService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ avisGeneraux: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: avisGeneraux }));
      saveSubject.complete();

      // THEN
      expect(avisGenerauxFormService.getAvisGeneraux).toHaveBeenCalled();
      expect(avisGenerauxService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAvisGeneraux>>();
      const avisGeneraux = { id: 123 };
      jest.spyOn(avisGenerauxService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ avisGeneraux });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(avisGenerauxService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
