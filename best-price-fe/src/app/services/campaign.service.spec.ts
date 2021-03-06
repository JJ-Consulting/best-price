import {TestBed, getTestBed} from '@angular/core/testing';

import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";

import {CampaignService}                from '@services';
import {Campaign}                       from "@models";
import {HTTP_INTERCEPTORS, HttpRequest} from "@angular/common/http";
import {AuthHttpHeader}                 from "../auth/auth.http.header";

describe('CampaignService', () => {
  let injector: TestBed;
  let service: CampaignService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:   [HttpClientTestingModule],
      providers: [
        {
          provide:  HTTP_INTERCEPTORS,
          useClass: AuthHttpHeader,
          multi:    true,
        },
        CampaignService
      ],
    });

    injector = getTestBed();
    service  = injector.get(CampaignService);
    httpMock = injector.get(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it("should get the user's campaigns", () => {
    // GIVEN
    localStorage.setItem('best-price-token', '42');
    const dummyCampaigns: Array<Campaign> = [
      {
        id:           1,
        name:         'Selling my car',
        price:        null,
        startDate:    new Date('2018-07-10T20:59:07+02:00'),
        endDate:      null,
        currency:     'USD',
        interactions: []
      },
      {
        id:           2,
        name:         'finding a job',
        price:        42,
        startDate:    new Date('2018-07-10T20:59:07+02:00'),
        endDate:      new Date('2018-07-15T20:59:07+02:00'),
        currency:     'USD',
        interactions: []
      },
    ];

    // WHEN
    service.getCampaigns().subscribe((campaigns: Array<Campaign>) => {
      expect(campaigns.length).toBe(2);
    });

    // THEN
    httpMock.expectOne((request: HttpRequest<any>) => {
      return request.method == 'GET'
        && request.url === CampaignService.CAMPAIGN_URL
        && request.headers.get('Authorization') === 'Bearer 42';
    }).flush(dummyCampaigns);
  });

  it('should get the campaign having the given ID', () => {
    // TODO
  });

  it('should create the given campaign', () => {
    const campaign: Campaign = new Campaign();

    campaign.name      = 'foobar';
    campaign.currency  = 'USD';
    campaign.startDate = new Date();

    service.createCampaign(campaign).subscribe();

    // GIVEN
    httpMock.expectOne((request: HttpRequest<any>) => {
      expect(request.body.name).toBe(campaign.name);
      expect(request.body.currency).toBe(campaign.currency);
      expect(request.body.startDate.toISOString()).toBe(campaign.startDate.toISOString());
      return request.method == 'POST'
        && request.url === CampaignService.CAMPAIGN_URL;
    }).flush({});
  });
});
