import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SituationMatrimonialeDetailComponent } from './situation-matrimoniale-detail.component';

describe('SituationMatrimoniale Management Detail Component', () => {
  let comp: SituationMatrimonialeDetailComponent;
  let fixture: ComponentFixture<SituationMatrimonialeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SituationMatrimonialeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ situationMatrimoniale: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SituationMatrimonialeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SituationMatrimonialeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load situationMatrimoniale on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.situationMatrimoniale).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
