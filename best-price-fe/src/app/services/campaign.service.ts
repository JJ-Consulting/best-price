import {Injectable}      from '@angular/core';
import {Campaign}        from "@models";
import {Observable}      from "rxjs/Observable";
import {HttpClient}      from "@angular/common/http";
import {BehaviorSubject} from "rxjs/BehaviorSubject";

@Injectable()
export class CampaignService {
  static readonly CAMPAIGN_URL: string = "/api/campaigns/";

  campaignLoaded: BehaviorSubject<number> = new BehaviorSubject(null);

  constructor(private httpClient: HttpClient) {
  }

  getCampaigns(): Observable<Array<Campaign>> {
    return this.httpClient.get<Array<Campaign>>(CampaignService.CAMPAIGN_URL);
  }

  getCampaign(id: string): Observable<Campaign> {
    return this.httpClient.get<Campaign>(CampaignService.CAMPAIGN_URL + id);
  }

  createCampaign(campaign: Campaign): Observable<Campaign> {
    return this.httpClient.post<Campaign>(CampaignService.CAMPAIGN_URL, campaign);
  }

  notifyCampaignLoaded(id: number): void {
    this.campaignLoaded.next(id);
  }
}
