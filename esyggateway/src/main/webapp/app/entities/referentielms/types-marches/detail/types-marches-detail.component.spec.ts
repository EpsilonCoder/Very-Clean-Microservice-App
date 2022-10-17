import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TypesMarchesDetailComponent } from './types-marches-detail.component';

describe('TypesMarches Management Detail Component', () => {
  let comp: TypesMarchesDetailComponent;
  let fixture: ComponentFixture<TypesMarchesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TypesMarchesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ typesMarches: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TypesMarchesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TypesMarchesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load typesMarches on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.typesMarches).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
