import {Component, OnInit}             from '@angular/core';
import {Observable}                    from "rxjs/Observable";
import {CampaignService, LoginService} from "@services";
import {Campaign}                      from "@models";
import {Router}                        from "@angular/router";
import {CampaignComponent}             from "../campaign/campaign.component";
import {MatDialog}                     from "@angular/material";

class CampaignViewModel extends Campaign {
  current: boolean = false;
}

@Component({
  selector:    'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls:   ['./side-bar.component.scss']
})
export class SideBarComponent implements OnInit {

  userLoggedIn$: Observable<boolean>;
  openedCampaigns: Array<CampaignViewModel> = [];
  closedCampaigns: Array<CampaignViewModel> = [];

  constructor(private loginService: LoginService, private campaignService: CampaignService, private router: Router,
              private dialog: MatDialog) {
    this.userLoggedIn$ = this.loginService.isLoggedIn;
    this.campaignService.campaignLoaded.subscribe((id: number) => {
      for (let campaign of this.openedCampaigns.concat(this.closedCampaigns)) {
        campaign.current = false;
        if (campaign.id === id) {
          campaign.current = true;
        }
      }
    })
  }

  ngOnInit() {
    this.loadCampaigns();
  }

  private loadCampaigns(): void {
    this.campaignService.getCampaigns().subscribe((campaigns: Array<Campaign>) => {
      this.openedCampaigns = [];
      this.closedCampaigns = [];
      for (let campaign of campaigns) {
        if (campaign.endDate) {
          this.closedCampaigns.push(new CampaignViewModel(campaign));
        } else {
          this.openedCampaigns.push(new CampaignViewModel(campaign));
        }
      }
    });
  }

  onCampaignClick(id: number): void {
    this.router.navigate(['campaigns', id]);
  }

  onNewCamapignClick(): void {
    const dialogRef = this.dialog.open(CampaignComponent, {
      width: '518px'
    });

    dialogRef.afterClosed().subscribe((newCampaignId: number) => {
      if (newCampaignId) {
        this.router.navigate(['campaigns', newCampaignId]);
        this.loadCampaigns();
      }
    });
  }
}
