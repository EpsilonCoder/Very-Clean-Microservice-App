import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ModeSelectionDetailComponent } from './mode-selection-detail.component';

describe('ModeSelection Management Detail Component', () => {
  let comp: ModeSelectionDetailComponent;
  let fixture: ComponentFixture<ModeSelectionDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModeSelectionDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ modeSelection: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ModeSelectionDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ModeSelectionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load modeSelection on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.modeSelection).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
