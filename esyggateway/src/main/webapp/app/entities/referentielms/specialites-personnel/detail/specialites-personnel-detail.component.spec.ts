import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SpecialitesPersonnelDetailComponent } from './specialites-personnel-detail.component';

describe('SpecialitesPersonnel Management Detail Component', () => {
  let comp: SpecialitesPersonnelDetailComponent;
  let fixture: ComponentFixture<SpecialitesPersonnelDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SpecialitesPersonnelDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ specialitesPersonnel: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SpecialitesPersonnelDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SpecialitesPersonnelDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load specialitesPersonnel on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.specialitesPersonnel).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
