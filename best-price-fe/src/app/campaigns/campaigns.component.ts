import {Component, OnInit}              from '@angular/core';
import {CampaignService}                from "@services";
import {Campaign, Contact, Interaction} from "@models";
import {Observable}                     from "rxjs/Observable";
import {ActivatedRoute, ParamMap}       from "@angular/router";
import {switchMap}                      from "rxjs/operators";

@Component({
  selector:    'app-campaigns',
  templateUrl: './campaigns.component.html',
  styleUrls:   ['./campaigns.component.scss']
})
export class CampaignsComponent implements OnInit {

  displayedColumns   = ['firstName', 'lastName', 'company', 'date', 'price'];
  campaigns$: Observable<Array<Campaign>>;
  campaign: Campaign = new Campaign();

  constructor(private campaignService: CampaignService, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.campaigns$ = this.campaignService.getCampaigns();

    this.route.paramMap.pipe(
      switchMap((params: ParamMap) =>
        this.campaignService.getCampaign(params.get('id')))
    ).subscribe((campaign: Campaign) => {
      const contact: Contact = new Contact;
      contact.firstName      = "foo";
      contact.lastName       = "bar";
      contact.company        = "acme";
      campaign.interactions.forEach((i: Interaction) => i.contact = contact);
      this.campaign = campaign;
    }, (error: any) => {

    });
  }

}
