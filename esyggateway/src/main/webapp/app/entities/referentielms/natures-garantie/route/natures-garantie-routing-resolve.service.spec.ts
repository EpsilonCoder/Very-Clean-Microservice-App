import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { INaturesGarantie } from '../natures-garantie.model';
import { NaturesGarantieService } from '../service/natures-garantie.service';

import { NaturesGarantieRoutingResolveService } from './natures-garantie-routing-resolve.service';

describe('NaturesGarantie routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: NaturesGarantieRoutingResolveService;
  let service: NaturesGarantieService;
  let resultNaturesGarantie: INaturesGarantie | null | undefined;

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
    routingResolveService = TestBed.inject(NaturesGarantieRoutingResolveService);
    service = TestBed.inject(NaturesGarantieService);
    resultNaturesGarantie = undefined;
  });

  describe('resolve', () => {
    it('should return INaturesGarantie returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNaturesGarantie = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultNaturesGarantie).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNaturesGarantie = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultNaturesGarantie).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<INaturesGarantie>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNaturesGarantie = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultNaturesGarantie).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
