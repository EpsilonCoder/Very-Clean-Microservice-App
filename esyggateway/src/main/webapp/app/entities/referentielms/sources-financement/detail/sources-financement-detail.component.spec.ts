import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SourcesFinancementDetailComponent } from './sources-financement-detail.component';

describe('SourcesFinancement Management Detail Component', () => {
  let comp: SourcesFinancementDetailComponent;
  let fixture: ComponentFixture<SourcesFinancementDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SourcesFinancementDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ sourcesFinancement: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SourcesFinancementDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SourcesFinancementDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load sourcesFinancement on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.sourcesFinancement).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
