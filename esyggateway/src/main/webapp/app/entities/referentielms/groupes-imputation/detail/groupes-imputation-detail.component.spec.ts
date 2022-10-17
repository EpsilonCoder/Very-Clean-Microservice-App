import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GroupesImputationDetailComponent } from './groupes-imputation-detail.component';

describe('GroupesImputation Management Detail Component', () => {
  let comp: GroupesImputationDetailComponent;
  let fixture: ComponentFixture<GroupesImputationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GroupesImputationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ groupesImputation: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(GroupesImputationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(GroupesImputationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load groupesImputation on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.groupesImputation).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
