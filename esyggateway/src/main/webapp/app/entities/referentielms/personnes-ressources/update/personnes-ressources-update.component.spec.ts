import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PersonnesRessourcesFormService } from './personnes-ressources-form.service';
import { PersonnesRessourcesService } from '../service/personnes-ressources.service';
import { IPersonnesRessources } from '../personnes-ressources.model';

import { PersonnesRessourcesUpdateComponent } from './personnes-ressources-update.component';

describe('PersonnesRessources Management Update Component', () => {
  let comp: PersonnesRessourcesUpdateComponent;
  let fixture: ComponentFixture<PersonnesRessourcesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let personnesRessourcesFormService: PersonnesRessourcesFormService;
  let personnesRessourcesService: PersonnesRessourcesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PersonnesRessourcesUpdateComponent],
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
      .overrideTemplate(PersonnesRessourcesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PersonnesRessourcesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    personnesRessourcesFormService = TestBed.inject(PersonnesRessourcesFormService);
    personnesRessourcesService = TestBed.inject(PersonnesRessourcesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const personnesRessources: IPersonnesRessources = { id: 456 };

      activatedRoute.data = of({ personnesRessources });
      comp.ngOnInit();

      expect(comp.personnesRessources).toEqual(personnesRessources);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPersonnesRessources>>();
      const personnesRessources = { id: 123 };
      jest.spyOn(personnesRessourcesFormService, 'getPersonnesRessources').mockReturnValue(personnesRessources);
      jest.spyOn(personnesRessourcesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personnesRessources });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: personnesRessources }));
      saveSubject.complete();

      // THEN
      expect(personnesRessourcesFormService.getPersonnesRessources).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(personnesRessourcesService.update).toHaveBeenCalledWith(expect.objectContaining(personnesRessources));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPersonnesRessources>>();
      const personnesRessources = { id: 123 };
      jest.spyOn(personnesRessourcesFormService, 'getPersonnesRessources').mockReturnValue({ id: null });
      jest.spyOn(personnesRessourcesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personnesRessources: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: personnesRessources }));
      saveSubject.complete();

      // THEN
      expect(personnesRessourcesFormService.getPersonnesRessources).toHaveBeenCalled();
      expect(personnesRessourcesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPersonnesRessources>>();
      const personnesRessources = { id: 123 };
      jest.spyOn(personnesRessourcesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personnesRessources });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(personnesRessourcesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
