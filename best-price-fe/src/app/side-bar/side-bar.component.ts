import {Component, OnInit}             from '@angular/core';
import {Observable}                    from "rxjs/Observable";
import {CampaignService, LoginService} from "@services";
import {Campaign}                      from "@models";
import {Router}                        from "@angular/router";

@Component({
  selector:    'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls:   ['./side-bar.component.scss']
})
export class SideBarComponent implements OnInit {

  userLoggedIn$: Observable<boolean>;
  openedCampaigns: Array<Campaign> = [];
  closedCampaigns: Array<Campaign> = [];

  constructor(private loginService: LoginService, private campaignService: CampaignService, private router: Router) {
    this.userLoggedIn$ = this.loginService.isLoggedIn;
  }

  ngOnInit() {
    this.campaignService.getCampaigns().subscribe((campaigns: Array<Campaign>) => {
      this.openedCampaigns = [];
      this.closedCampaigns = [];
      for (let campaign of campaigns) {
        if (campaign.endDate) {
          this.closedCampaigns.push(campaign);
        } else {
          this.openedCampaigns.push(campaign);
        }
      }
    });
  }

  onCampaignClick(id: number): void {
    this.router.navigate(['campaigns', id]);
  }
}
