import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CategorieFournisseurDetailComponent } from './categorie-fournisseur-detail.component';

describe('CategorieFournisseur Management Detail Component', () => {
  let comp: CategorieFournisseurDetailComponent;
  let fixture: ComponentFixture<CategorieFournisseurDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CategorieFournisseurDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ categorieFournisseur: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CategorieFournisseurDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CategorieFournisseurDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load categorieFournisseur on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.categorieFournisseur).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
