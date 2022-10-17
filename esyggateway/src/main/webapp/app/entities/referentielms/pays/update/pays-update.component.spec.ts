import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PaysFormService } from './pays-form.service';
import { PaysService } from '../service/pays.service';
import { IPays } from '../pays.model';

import { PaysUpdateComponent } from './pays-update.component';

describe('Pays Management Update Component', () => {
  let comp: PaysUpdateComponent;
  let fixture: ComponentFixture<PaysUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let paysFormService: PaysFormService;
  let paysService: PaysService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PaysUpdateComponent],
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
      .overrideTemplate(PaysUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaysUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    paysFormService = TestBed.inject(PaysFormService);
    paysService = TestBed.inject(PaysService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const pays: IPays = { id: 456 };

      activatedRoute.data = of({ pays });
      comp.ngOnInit();

      expect(comp.pays).toEqual(pays);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPays>>();
      const pays = { id: 123 };
      jest.spyOn(paysFormService, 'getPays').mockReturnValue(pays);
      jest.spyOn(paysService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pays });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pays }));
      saveSubject.complete();

      // THEN
      expect(paysFormService.getPays).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(paysService.update).toHaveBeenCalledWith(expect.objectContaining(pays));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPays>>();
      const pays = { id: 123 };
      jest.spyOn(paysFormService, 'getPays').mockReturnValue({ id: null });
      jest.spyOn(paysService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pays: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pays }));
      saveSubject.complete();

      // THEN
      expect(paysFormService.getPays).toHaveBeenCalled();
      expect(paysService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPays>>();
      const pays = { id: 123 };
      jest.spyOn(paysService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pays });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(paysService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
