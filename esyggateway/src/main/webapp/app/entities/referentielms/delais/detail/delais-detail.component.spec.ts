import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DelaisDetailComponent } from './delais-detail.component';

describe('Delais Management Detail Component', () => {
  let comp: DelaisDetailComponent;
  let fixture: ComponentFixture<DelaisDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DelaisDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ delais: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DelaisDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DelaisDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load delais on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.delais).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
