import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NaturesGarantieDetailComponent } from './natures-garantie-detail.component';

describe('NaturesGarantie Management Detail Component', () => {
  let comp: NaturesGarantieDetailComponent;
  let fixture: ComponentFixture<NaturesGarantieDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NaturesGarantieDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ naturesGarantie: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NaturesGarantieDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NaturesGarantieDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load naturesGarantie on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.naturesGarantie).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
