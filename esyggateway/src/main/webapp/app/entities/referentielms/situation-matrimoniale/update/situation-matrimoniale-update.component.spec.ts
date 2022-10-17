import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SituationMatrimonialeFormService } from './situation-matrimoniale-form.service';
import { SituationMatrimonialeService } from '../service/situation-matrimoniale.service';
import { ISituationMatrimoniale } from '../situation-matrimoniale.model';

import { SituationMatrimonialeUpdateComponent } from './situation-matrimoniale-update.component';

describe('SituationMatrimoniale Management Update Component', () => {
  let comp: SituationMatrimonialeUpdateComponent;
  let fixture: ComponentFixture<SituationMatrimonialeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let situationMatrimonialeFormService: SituationMatrimonialeFormService;
  let situationMatrimonialeService: SituationMatrimonialeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SituationMatrimonialeUpdateComponent],
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
      .overrideTemplate(SituationMatrimonialeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SituationMatrimonialeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    situationMatrimonialeFormService = TestBed.inject(SituationMatrimonialeFormService);
    situationMatrimonialeService = TestBed.inject(SituationMatrimonialeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const situationMatrimoniale: ISituationMatrimoniale = { id: 456 };

      activatedRoute.data = of({ situationMatrimoniale });
      comp.ngOnInit();

      expect(comp.situationMatrimoniale).toEqual(situationMatrimoniale);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISituationMatrimoniale>>();
      const situationMatrimoniale = { id: 123 };
      jest.spyOn(situationMatrimonialeFormService, 'getSituationMatrimoniale').mockReturnValue(situationMatrimoniale);
      jest.spyOn(situationMatrimonialeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ situationMatrimoniale });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: situationMatrimoniale }));
      saveSubject.complete();

      // THEN
      expect(situationMatrimonialeFormService.getSituationMatrimoniale).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(situationMatrimonialeService.update).toHaveBeenCalledWith(expect.objectContaining(situationMatrimoniale));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISituationMatrimoniale>>();
      const situationMatrimoniale = { id: 123 };
      jest.spyOn(situationMatrimonialeFormService, 'getSituationMatrimoniale').mockReturnValue({ id: null });
      jest.spyOn(situationMatrimonialeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ situationMatrimoniale: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: situationMatrimoniale }));
      saveSubject.complete();

      // THEN
      expect(situationMatrimonialeFormService.getSituationMatrimoniale).toHaveBeenCalled();
      expect(situationMatrimonialeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISituationMatrimoniale>>();
      const situationMatrimoniale = { id: 123 };
      jest.spyOn(situationMatrimonialeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ situationMatrimoniale });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(situationMatrimonialeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
