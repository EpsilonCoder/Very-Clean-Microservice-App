import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GarantieDetailComponent } from './garantie-detail.component';

describe('Garantie Management Detail Component', () => {
  let comp: GarantieDetailComponent;
  let fixture: ComponentFixture<GarantieDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GarantieDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ garantie: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(GarantieDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(GarantieDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load garantie on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.garantie).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
