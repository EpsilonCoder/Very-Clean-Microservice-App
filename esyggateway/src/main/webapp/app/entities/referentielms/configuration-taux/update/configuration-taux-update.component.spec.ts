import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ConfigurationTauxFormService } from './configuration-taux-form.service';
import { ConfigurationTauxService } from '../service/configuration-taux.service';
import { IConfigurationTaux } from '../configuration-taux.model';
import { IPays } from 'app/entities/referentielms/pays/pays.model';
import { PaysService } from 'app/entities/referentielms/pays/service/pays.service';

import { ConfigurationTauxUpdateComponent } from './configuration-taux-update.component';

describe('ConfigurationTaux Management Update Component', () => {
  let comp: ConfigurationTauxUpdateComponent;
  let fixture: ComponentFixture<ConfigurationTauxUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let configurationTauxFormService: ConfigurationTauxFormService;
  let configurationTauxService: ConfigurationTauxService;
  let paysService: PaysService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ConfigurationTauxUpdateComponent],
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
      .overrideTemplate(ConfigurationTauxUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ConfigurationTauxUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    configurationTauxFormService = TestBed.inject(ConfigurationTauxFormService);
    configurationTauxService = TestBed.inject(ConfigurationTauxService);
    paysService = TestBed.inject(PaysService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Pays query and add missing value', () => {
      const configurationTaux: IConfigurationTaux = { id: 456 };
      const pays: IPays = { id: 37020 };
      configurationTaux.pays = pays;

      const paysCollection: IPays[] = [{ id: 71843 }];
      jest.spyOn(paysService, 'query').mockReturnValue(of(new HttpResponse({ body: paysCollection })));
      const additionalPays = [pays];
      const expectedCollection: IPays[] = [...additionalPays, ...paysCollection];
      jest.spyOn(paysService, 'addPaysToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ configurationTaux });
      comp.ngOnInit();

      expect(paysService.query).toHaveBeenCalled();
      expect(paysService.addPaysToCollectionIfMissing).toHaveBeenCalledWith(paysCollection, ...additionalPays.map(expect.objectContaining));
      expect(comp.paysSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const configurationTaux: IConfigurationTaux = { id: 456 };
      const pays: IPays = { id: 88014 };
      configurationTaux.pays = pays;

      activatedRoute.data = of({ configurationTaux });
      comp.ngOnInit();

      expect(comp.paysSharedCollection).toContain(pays);
      expect(comp.configurationTaux).toEqual(configurationTaux);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IConfigurationTaux>>();
      const configurationTaux = { id: 123 };
      jest.spyOn(configurationTauxFormService, 'getConfigurationTaux').mockReturnValue(configurationTaux);
      jest.spyOn(configurationTauxService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ configurationTaux });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: configurationTaux }));
      saveSubject.complete();

      // THEN
      expect(configurationTauxFormService.getConfigurationTaux).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(configurationTauxService.update).toHaveBeenCalledWith(expect.objectContaining(configurationTaux));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IConfigurationTaux>>();
      const configurationTaux = { id: 123 };
      jest.spyOn(configurationTauxFormService, 'getConfigurationTaux').mockReturnValue({ id: null });
      jest.spyOn(configurationTauxService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ configurationTaux: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: configurationTaux }));
      saveSubject.complete();

      // THEN
      expect(configurationTauxFormService.getConfigurationTaux).toHaveBeenCalled();
      expect(configurationTauxService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IConfigurationTaux>>();
      const configurationTaux = { id: 123 };
      jest.spyOn(configurationTauxService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ configurationTaux });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(configurationTauxService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePays', () => {
      it('Should forward to paysService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(paysService, 'comparePays');
        comp.comparePays(entity, entity2);
        expect(paysService.comparePays).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
