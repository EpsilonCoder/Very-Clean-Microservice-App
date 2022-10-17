import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SpecialitesPersonnelFormService } from './specialites-personnel-form.service';
import { SpecialitesPersonnelService } from '../service/specialites-personnel.service';
import { ISpecialitesPersonnel } from '../specialites-personnel.model';

import { SpecialitesPersonnelUpdateComponent } from './specialites-personnel-update.component';

describe('SpecialitesPersonnel Management Update Component', () => {
  let comp: SpecialitesPersonnelUpdateComponent;
  let fixture: ComponentFixture<SpecialitesPersonnelUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let specialitesPersonnelFormService: SpecialitesPersonnelFormService;
  let specialitesPersonnelService: SpecialitesPersonnelService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SpecialitesPersonnelUpdateComponent],
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
      .overrideTemplate(SpecialitesPersonnelUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SpecialitesPersonnelUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    specialitesPersonnelFormService = TestBed.inject(SpecialitesPersonnelFormService);
    specialitesPersonnelService = TestBed.inject(SpecialitesPersonnelService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const specialitesPersonnel: ISpecialitesPersonnel = { id: 456 };

      activatedRoute.data = of({ specialitesPersonnel });
      comp.ngOnInit();

      expect(comp.specialitesPersonnel).toEqual(specialitesPersonnel);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISpecialitesPersonnel>>();
      const specialitesPersonnel = { id: 123 };
      jest.spyOn(specialitesPersonnelFormService, 'getSpecialitesPersonnel').mockReturnValue(specialitesPersonnel);
      jest.spyOn(specialitesPersonnelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ specialitesPersonnel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: specialitesPersonnel }));
      saveSubject.complete();

      // THEN
      expect(specialitesPersonnelFormService.getSpecialitesPersonnel).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(specialitesPersonnelService.update).toHaveBeenCalledWith(expect.objectContaining(specialitesPersonnel));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISpecialitesPersonnel>>();
      const specialitesPersonnel = { id: 123 };
      jest.spyOn(specialitesPersonnelFormService, 'getSpecialitesPersonnel').mockReturnValue({ id: null });
      jest.spyOn(specialitesPersonnelService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ specialitesPersonnel: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: specialitesPersonnel }));
      saveSubject.complete();

      // THEN
      expect(specialitesPersonnelFormService.getSpecialitesPersonnel).toHaveBeenCalled();
      expect(specialitesPersonnelService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISpecialitesPersonnel>>();
      const specialitesPersonnel = { id: 123 };
      jest.spyOn(specialitesPersonnelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ specialitesPersonnel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(specialitesPersonnelService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
