import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ISygAutoriteContractante } from '../syg-autorite-contractante.model';
import { SygAutoriteContractanteService } from '../service/syg-autorite-contractante.service';

import { SygAutoriteContractanteRoutingResolveService } from './syg-autorite-contractante-routing-resolve.service';

describe('SygAutoriteContractante routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SygAutoriteContractanteRoutingResolveService;
  let service: SygAutoriteContractanteService;
  let resultSygAutoriteContractante: ISygAutoriteContractante | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(SygAutoriteContractanteRoutingResolveService);
    service = TestBed.inject(SygAutoriteContractanteService);
    resultSygAutoriteContractante = undefined;
  });

  describe('resolve', () => {
    it('should return ISygAutoriteContractante returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSygAutoriteContractante = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSygAutoriteContractante).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSygAutoriteContractante = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSygAutoriteContractante).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ISygAutoriteContractante>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSygAutoriteContractante = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSygAutoriteContractante).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
