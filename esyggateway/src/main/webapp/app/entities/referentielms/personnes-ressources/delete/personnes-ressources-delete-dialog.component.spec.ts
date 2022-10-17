jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { PersonnesRessourcesService } from '../service/personnes-ressources.service';

import { PersonnesRessourcesDeleteDialogComponent } from './personnes-ressources-delete-dialog.component';

describe('PersonnesRessources Management Delete Component', () => {
  let comp: PersonnesRessourcesDeleteDialogComponent;
  let fixture: ComponentFixture<PersonnesRessourcesDeleteDialogComponent>;
  let service: PersonnesRessourcesService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PersonnesRessourcesDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(PersonnesRessourcesDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PersonnesRessourcesDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PersonnesRessourcesService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
