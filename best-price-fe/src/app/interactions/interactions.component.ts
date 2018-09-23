import {Component, OnInit}              from '@angular/core';
import {CampaignService}                from "@services";
import {Campaign, Contact, Interaction} from "@models";
import {ActivatedRoute, ParamMap}       from "@angular/router";
import {switchMap}                      from "rxjs/operators";

@Component({
  selector:    'app-interactions',
  templateUrl: './interactions.component.html',
  styleUrls:   ['./interactions.component.scss']
})
export class InteractionsComponent implements OnInit {

  displayedColumns   = ['firstName', 'lastName', 'company', 'date', 'price'];
  campaign: Campaign = new Campaign();

  constructor(private campaignService: CampaignService, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.paramMap.pipe(
      switchMap((params: ParamMap) =>
        this.campaignService.getCampaign(params.get('id')))
    ).subscribe((campaign: Campaign) => {
      this.campaignService.notifyCampaignLoaded(campaign.id);
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
