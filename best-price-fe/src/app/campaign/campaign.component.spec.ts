import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ReactiveFormsModule} from "@angular/forms";

import {CampaignComponent}       from './campaign.component';
import {MaterialModule}          from "../material.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatDialogRef}            from "@angular/material";

describe('CampaignComponent', () => {
  let component: CampaignComponent;
  let fixture: ComponentFixture<CampaignComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports:      [ReactiveFormsModule, MaterialModule, BrowserAnimationsModule],
      declarations: [CampaignComponent],
      providers:    [{provide: MatDialogRef, useValue: {}}]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture   = TestBed.createComponent(CampaignComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
