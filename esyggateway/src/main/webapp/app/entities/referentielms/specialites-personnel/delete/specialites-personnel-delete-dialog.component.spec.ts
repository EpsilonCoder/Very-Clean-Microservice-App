jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { SpecialitesPersonnelService } from '../service/specialites-personnel.service';

import { SpecialitesPersonnelDeleteDialogComponent } from './specialites-personnel-delete-dialog.component';

describe('SpecialitesPersonnel Management Delete Component', () => {
  let comp: SpecialitesPersonnelDeleteDialogComponent;
  let fixture: ComponentFixture<SpecialitesPersonnelDeleteDialogComponent>;
  let service: SpecialitesPersonnelService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SpecialitesPersonnelDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(SpecialitesPersonnelDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SpecialitesPersonnelDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SpecialitesPersonnelService);
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
