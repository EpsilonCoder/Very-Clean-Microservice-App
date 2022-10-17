import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DirectionFormService } from './direction-form.service';
import { DirectionService } from '../service/direction.service';
import { IDirection } from '../direction.model';

import { DirectionUpdateComponent } from './direction-update.component';

describe('Direction Management Update Component', () => {
  let comp: DirectionUpdateComponent;
  let fixture: ComponentFixture<DirectionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let directionFormService: DirectionFormService;
  let directionService: DirectionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DirectionUpdateComponent],
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
      .overrideTemplate(DirectionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DirectionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    directionFormService = TestBed.inject(DirectionFormService);
    directionService = TestBed.inject(DirectionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const direction: IDirection = { id: 456 };

      activatedRoute.data = of({ direction });
      comp.ngOnInit();

      expect(comp.direction).toEqual(direction);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDirection>>();
      const direction = { id: 123 };
      jest.spyOn(directionFormService, 'getDirection').mockReturnValue(direction);
      jest.spyOn(directionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ direction });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: direction }));
      saveSubject.complete();

      // THEN
      expect(directionFormService.getDirection).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(directionService.update).toHaveBeenCalledWith(expect.objectContaining(direction));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDirection>>();
      const direction = { id: 123 };
      jest.spyOn(directionFormService, 'getDirection').mockReturnValue({ id: null });
      jest.spyOn(directionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ direction: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: direction }));
      saveSubject.complete();

      // THEN
      expect(directionFormService.getDirection).toHaveBeenCalled();
      expect(directionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDirection>>();
      const direction = { id: 123 };
      jest.spyOn(directionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ direction });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(directionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
