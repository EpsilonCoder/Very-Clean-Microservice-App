import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PiecesAdministrativesDetailComponent } from './pieces-administratives-detail.component';

describe('PiecesAdministratives Management Detail Component', () => {
  let comp: PiecesAdministrativesDetailComponent;
  let fixture: ComponentFixture<PiecesAdministrativesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PiecesAdministrativesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ piecesAdministratives: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PiecesAdministrativesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PiecesAdministrativesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load piecesAdministratives on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.piecesAdministratives).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
