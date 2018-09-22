import {Component, OnInit}                  from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatDialogRef}                       from "@angular/material";

@Component({
  selector:    'app-campaign',
  templateUrl: './campaign.component.html',
  styleUrls:   ['./campaign.component.css']
})
export class CampaignComponent implements OnInit {

  public campaignForm: FormGroup;
  public actionDisabled: Boolean = false;

  constructor(private fb: FormBuilder, private dialogRef: MatDialogRef<CampaignComponent>) {
    this.createForm();
  }

  ngOnInit() {
  }

  onSubmit(): void {
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  private createForm(): void {
    this.campaignForm = this.fb.group({
      name:      ['', Validators.required],
      currency:  ['', Validators.required],
      startDate: ['', Validators.required]
    });
  }
}
