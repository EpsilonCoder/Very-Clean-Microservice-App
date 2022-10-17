import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PersonnesRessourcesDetailComponent } from './personnes-ressources-detail.component';

describe('PersonnesRessources Management Detail Component', () => {
  let comp: PersonnesRessourcesDetailComponent;
  let fixture: ComponentFixture<PersonnesRessourcesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PersonnesRessourcesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ personnesRessources: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PersonnesRessourcesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PersonnesRessourcesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load personnesRessources on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.personnesRessources).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
