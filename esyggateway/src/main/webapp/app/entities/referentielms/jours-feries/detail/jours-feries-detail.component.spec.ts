import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JoursFeriesDetailComponent } from './jours-feries-detail.component';

describe('JoursFeries Management Detail Component', () => {
  let comp: JoursFeriesDetailComponent;
  let fixture: ComponentFixture<JoursFeriesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [JoursFeriesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ joursFeries: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(JoursFeriesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(JoursFeriesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load joursFeries on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.joursFeries).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
