import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ModeSelectionFormService } from './mode-selection-form.service';
import { ModeSelectionService } from '../service/mode-selection.service';
import { IModeSelection } from '../mode-selection.model';

import { ModeSelectionUpdateComponent } from './mode-selection-update.component';

describe('ModeSelection Management Update Component', () => {
  let comp: ModeSelectionUpdateComponent;
  let fixture: ComponentFixture<ModeSelectionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let modeSelectionFormService: ModeSelectionFormService;
  let modeSelectionService: ModeSelectionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ModeSelectionUpdateComponent],
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
      .overrideTemplate(ModeSelectionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ModeSelectionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    modeSelectionFormService = TestBed.inject(ModeSelectionFormService);
    modeSelectionService = TestBed.inject(ModeSelectionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const modeSelection: IModeSelection = { id: 456 };

      activatedRoute.data = of({ modeSelection });
      comp.ngOnInit();

      expect(comp.modeSelection).toEqual(modeSelection);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IModeSelection>>();
      const modeSelection = { id: 123 };
      jest.spyOn(modeSelectionFormService, 'getModeSelection').mockReturnValue(modeSelection);
      jest.spyOn(modeSelectionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ modeSelection });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: modeSelection }));
      saveSubject.complete();

      // THEN
      expect(modeSelectionFormService.getModeSelection).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(modeSelectionService.update).toHaveBeenCalledWith(expect.objectContaining(modeSelection));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IModeSelection>>();
      const modeSelection = { id: 123 };
      jest.spyOn(modeSelectionFormService, 'getModeSelection').mockReturnValue({ id: null });
      jest.spyOn(modeSelectionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ modeSelection: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: modeSelection }));
      saveSubject.complete();

      // THEN
      expect(modeSelectionFormService.getModeSelection).toHaveBeenCalled();
      expect(modeSelectionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IModeSelection>>();
      const modeSelection = { id: 123 };
      jest.spyOn(modeSelectionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ modeSelection });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(modeSelectionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
