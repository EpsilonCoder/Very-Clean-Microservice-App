import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IAutoriteContractante } from '../autorite-contractante.model';
import { AutoriteContractanteService } from '../service/autorite-contractante.service';

import { AutoriteContractanteRoutingResolveService } from './autorite-contractante-routing-resolve.service';

describe('AutoriteContractante routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: AutoriteContractanteRoutingResolveService;
  let service: AutoriteContractanteService;
  let resultAutoriteContractante: IAutoriteContractante | null | undefined;

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
    routingResolveService = TestBed.inject(AutoriteContractanteRoutingResolveService);
    service = TestBed.inject(AutoriteContractanteService);
    resultAutoriteContractante = undefined;
  });

  describe('resolve', () => {
    it('should return IAutoriteContractante returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAutoriteContractante = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAutoriteContractante).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAutoriteContractante = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAutoriteContractante).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAutoriteContractante>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAutoriteContractante = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAutoriteContractante).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
