import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NaturesGarantieFormService } from './natures-garantie-form.service';
import { NaturesGarantieService } from '../service/natures-garantie.service';
import { INaturesGarantie } from '../natures-garantie.model';

import { NaturesGarantieUpdateComponent } from './natures-garantie-update.component';

describe('NaturesGarantie Management Update Component', () => {
  let comp: NaturesGarantieUpdateComponent;
  let fixture: ComponentFixture<NaturesGarantieUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let naturesGarantieFormService: NaturesGarantieFormService;
  let naturesGarantieService: NaturesGarantieService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NaturesGarantieUpdateComponent],
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
      .overrideTemplate(NaturesGarantieUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NaturesGarantieUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    naturesGarantieFormService = TestBed.inject(NaturesGarantieFormService);
    naturesGarantieService = TestBed.inject(NaturesGarantieService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const naturesGarantie: INaturesGarantie = { id: 456 };

      activatedRoute.data = of({ naturesGarantie });
      comp.ngOnInit();

      expect(comp.naturesGarantie).toEqual(naturesGarantie);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INaturesGarantie>>();
      const naturesGarantie = { id: 123 };
      jest.spyOn(naturesGarantieFormService, 'getNaturesGarantie').mockReturnValue(naturesGarantie);
      jest.spyOn(naturesGarantieService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ naturesGarantie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: naturesGarantie }));
      saveSubject.complete();

      // THEN
      expect(naturesGarantieFormService.getNaturesGarantie).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(naturesGarantieService.update).toHaveBeenCalledWith(expect.objectContaining(naturesGarantie));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INaturesGarantie>>();
      const naturesGarantie = { id: 123 };
      jest.spyOn(naturesGarantieFormService, 'getNaturesGarantie').mockReturnValue({ id: null });
      jest.spyOn(naturesGarantieService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ naturesGarantie: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: naturesGarantie }));
      saveSubject.complete();

      // THEN
      expect(naturesGarantieFormService.getNaturesGarantie).toHaveBeenCalled();
      expect(naturesGarantieService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INaturesGarantie>>();
      const naturesGarantie = { id: 123 };
      jest.spyOn(naturesGarantieService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ naturesGarantie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(naturesGarantieService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
