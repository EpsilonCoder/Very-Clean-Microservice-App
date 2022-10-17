import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConfigurationTauxDetailComponent } from './configuration-taux-detail.component';

describe('ConfigurationTaux Management Detail Component', () => {
  let comp: ConfigurationTauxDetailComponent;
  let fixture: ComponentFixture<ConfigurationTauxDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ConfigurationTauxDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ configurationTaux: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ConfigurationTauxDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ConfigurationTauxDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load configurationTaux on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.configurationTaux).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
