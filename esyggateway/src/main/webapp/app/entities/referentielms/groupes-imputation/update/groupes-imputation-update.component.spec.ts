import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { GroupesImputationFormService } from './groupes-imputation-form.service';
import { GroupesImputationService } from '../service/groupes-imputation.service';
import { IGroupesImputation } from '../groupes-imputation.model';

import { GroupesImputationUpdateComponent } from './groupes-imputation-update.component';

describe('GroupesImputation Management Update Component', () => {
  let comp: GroupesImputationUpdateComponent;
  let fixture: ComponentFixture<GroupesImputationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let groupesImputationFormService: GroupesImputationFormService;
  let groupesImputationService: GroupesImputationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [GroupesImputationUpdateComponent],
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
      .overrideTemplate(GroupesImputationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GroupesImputationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    groupesImputationFormService = TestBed.inject(GroupesImputationFormService);
    groupesImputationService = TestBed.inject(GroupesImputationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const groupesImputation: IGroupesImputation = { id: 456 };

      activatedRoute.data = of({ groupesImputation });
      comp.ngOnInit();

      expect(comp.groupesImputation).toEqual(groupesImputation);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGroupesImputation>>();
      const groupesImputation = { id: 123 };
      jest.spyOn(groupesImputationFormService, 'getGroupesImputation').mockReturnValue(groupesImputation);
      jest.spyOn(groupesImputationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ groupesImputation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: groupesImputation }));
      saveSubject.complete();

      // THEN
      expect(groupesImputationFormService.getGroupesImputation).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(groupesImputationService.update).toHaveBeenCalledWith(expect.objectContaining(groupesImputation));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGroupesImputation>>();
      const groupesImputation = { id: 123 };
      jest.spyOn(groupesImputationFormService, 'getGroupesImputation').mockReturnValue({ id: null });
      jest.spyOn(groupesImputationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ groupesImputation: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: groupesImputation }));
      saveSubject.complete();

      // THEN
      expect(groupesImputationFormService.getGroupesImputation).toHaveBeenCalled();
      expect(groupesImputationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGroupesImputation>>();
      const groupesImputation = { id: 123 };
      jest.spyOn(groupesImputationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ groupesImputation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(groupesImputationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
