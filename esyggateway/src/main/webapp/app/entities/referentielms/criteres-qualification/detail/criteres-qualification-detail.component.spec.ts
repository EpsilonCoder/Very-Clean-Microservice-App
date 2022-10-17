import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CriteresQualificationDetailComponent } from './criteres-qualification-detail.component';

describe('CriteresQualification Management Detail Component', () => {
  let comp: CriteresQualificationDetailComponent;
  let fixture: ComponentFixture<CriteresQualificationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CriteresQualificationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ criteresQualification: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CriteresQualificationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CriteresQualificationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load criteresQualification on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.criteresQualification).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
