import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PiecesAdministrativesFormService } from './pieces-administratives-form.service';
import { PiecesAdministrativesService } from '../service/pieces-administratives.service';
import { IPiecesAdministratives } from '../pieces-administratives.model';

import { PiecesAdministrativesUpdateComponent } from './pieces-administratives-update.component';

describe('PiecesAdministratives Management Update Component', () => {
  let comp: PiecesAdministrativesUpdateComponent;
  let fixture: ComponentFixture<PiecesAdministrativesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let piecesAdministrativesFormService: PiecesAdministrativesFormService;
  let piecesAdministrativesService: PiecesAdministrativesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PiecesAdministrativesUpdateComponent],
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
      .overrideTemplate(PiecesAdministrativesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PiecesAdministrativesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    piecesAdministrativesFormService = TestBed.inject(PiecesAdministrativesFormService);
    piecesAdministrativesService = TestBed.inject(PiecesAdministrativesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const piecesAdministratives: IPiecesAdministratives = { id: 456 };

      activatedRoute.data = of({ piecesAdministratives });
      comp.ngOnInit();

      expect(comp.piecesAdministratives).toEqual(piecesAdministratives);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPiecesAdministratives>>();
      const piecesAdministratives = { id: 123 };
      jest.spyOn(piecesAdministrativesFormService, 'getPiecesAdministratives').mockReturnValue(piecesAdministratives);
      jest.spyOn(piecesAdministrativesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ piecesAdministratives });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: piecesAdministratives }));
      saveSubject.complete();

      // THEN
      expect(piecesAdministrativesFormService.getPiecesAdministratives).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(piecesAdministrativesService.update).toHaveBeenCalledWith(expect.objectContaining(piecesAdministratives));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPiecesAdministratives>>();
      const piecesAdministratives = { id: 123 };
      jest.spyOn(piecesAdministrativesFormService, 'getPiecesAdministratives').mockReturnValue({ id: null });
      jest.spyOn(piecesAdministrativesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ piecesAdministratives: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: piecesAdministratives }));
      saveSubject.complete();

      // THEN
      expect(piecesAdministrativesFormService.getPiecesAdministratives).toHaveBeenCalled();
      expect(piecesAdministrativesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPiecesAdministratives>>();
      const piecesAdministratives = { id: 123 };
      jest.spyOn(piecesAdministrativesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ piecesAdministratives });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(piecesAdministrativesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
