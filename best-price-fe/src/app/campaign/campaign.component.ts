import {Component, OnInit}                  from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatDialogRef}                       from "@angular/material";
import {CampaignService}                    from "@services";
import {Campaign}                           from "@models";

@Component({
  selector:    'app-campaign',
  templateUrl: './campaign.component.html',
  styleUrls:   ['./campaign.component.css']
})
export class CampaignComponent implements OnInit {

  public campaignForm: FormGroup;
  public actionDisabled: Boolean = false;

  constructor(private fb: FormBuilder, private dialogRef: MatDialogRef<CampaignComponent>,
              private campaignService: CampaignService) {
    this.createForm();
  }

  ngOnInit() {
  }

  onSubmit(): void {
    this.actionDisabled = true;
    const campaign: Campaign = new Campaign();
    campaign.name = this.campaignForm.value.name;
    campaign.currency = this.campaignForm.value.currency;
    campaign.startDate = new Date(this.campaignForm.value.startDate);

    this.campaignService.createCampaign(campaign).subscribe((campaign: Campaign) => {
      this.actionDisabled = false;
      this.dialogRef.close(campaign.id);
    });
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  private createForm(): void {
    this.campaignForm = this.fb.group({
      name:      ['', Validators.required],
      currency:  ['', Validators.required],
      startDate: [new Date().toISOString().substring(0,10), Validators.required]
    });
  }
}
