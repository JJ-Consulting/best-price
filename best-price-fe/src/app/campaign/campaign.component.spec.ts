import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ReactiveFormsModule} from "@angular/forms";

import {CampaignComponent}       from './campaign.component';
import {MaterialModule}          from "../material.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatDialogRef}            from "@angular/material";
import {CampaignService}         from "@services";
import {Observable}              from "rxjs/Observable";
import {Campaign}                from "@models";
import {from}                    from "rxjs/observable/from";

class CampaignServiceMock {
  createCampaign(): Observable<void> {
    return null;
  }
}

class DialogRefMock {
  close(): void {}
}

describe('CampaignComponent', () => {
  let component: CampaignComponent;
  let fixture: ComponentFixture<CampaignComponent>;
  const campaignServiceMock: CampaignServiceMock = new CampaignServiceMock();
  const dialogRefMock = new DialogRefMock();

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports:      [ReactiveFormsModule, MaterialModule, BrowserAnimationsModule],
      declarations: [CampaignComponent],
      providers:    [
        {provide: MatDialogRef, useValue: dialogRefMock},
        {provide: CampaignService, useValue: campaignServiceMock}
      ]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture   = TestBed.createComponent(CampaignComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should not validate form when empty', () => {
    expect(component.campaignForm.valid).toBeFalsy();
  });

  it('should call CampaignService#create', () => {
    // GIVEN
    let actual: Campaign = null;
    spyOn(campaignServiceMock, 'createCampaign').and.callFake((campaign: Campaign) => {
      actual = campaign;
      return from([{id: 42}]);
    });
    spyOn(dialogRefMock, 'close');
    component.campaignForm.controls['name'].setValue('foobar');
    component.campaignForm.controls['currency'].setValue('USD');

    // WHEN
    component.onSubmit();

    // THEN
    expect(actual.name).toBe('foobar');
    expect(actual.currency).toBe('USD');
    expect(actual.startDate.toISOString().substring(0, 10)).toBe(new Date().toISOString().substring(0,10));
    expect(dialogRefMock.close).toHaveBeenCalledWith(42);
  });
});
