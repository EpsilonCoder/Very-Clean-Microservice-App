import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SourcesFinancementFormService } from './sources-financement-form.service';
import { SourcesFinancementService } from '../service/sources-financement.service';
import { ISourcesFinancement } from '../sources-financement.model';

import { SourcesFinancementUpdateComponent } from './sources-financement-update.component';

describe('SourcesFinancement Management Update Component', () => {
  let comp: SourcesFinancementUpdateComponent;
  let fixture: ComponentFixture<SourcesFinancementUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sourcesFinancementFormService: SourcesFinancementFormService;
  let sourcesFinancementService: SourcesFinancementService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SourcesFinancementUpdateComponent],
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
      .overrideTemplate(SourcesFinancementUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SourcesFinancementUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sourcesFinancementFormService = TestBed.inject(SourcesFinancementFormService);
    sourcesFinancementService = TestBed.inject(SourcesFinancementService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const sourcesFinancement: ISourcesFinancement = { id: 456 };

      activatedRoute.data = of({ sourcesFinancement });
      comp.ngOnInit();

      expect(comp.sourcesFinancement).toEqual(sourcesFinancement);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISourcesFinancement>>();
      const sourcesFinancement = { id: 123 };
      jest.spyOn(sourcesFinancementFormService, 'getSourcesFinancement').mockReturnValue(sourcesFinancement);
      jest.spyOn(sourcesFinancementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sourcesFinancement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sourcesFinancement }));
      saveSubject.complete();

      // THEN
      expect(sourcesFinancementFormService.getSourcesFinancement).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(sourcesFinancementService.update).toHaveBeenCalledWith(expect.objectContaining(sourcesFinancement));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISourcesFinancement>>();
      const sourcesFinancement = { id: 123 };
      jest.spyOn(sourcesFinancementFormService, 'getSourcesFinancement').mockReturnValue({ id: null });
      jest.spyOn(sourcesFinancementService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sourcesFinancement: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sourcesFinancement }));
      saveSubject.complete();

      // THEN
      expect(sourcesFinancementFormService.getSourcesFinancement).toHaveBeenCalled();
      expect(sourcesFinancementService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISourcesFinancement>>();
      const sourcesFinancement = { id: 123 };
      jest.spyOn(sourcesFinancementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sourcesFinancement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sourcesFinancementService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
