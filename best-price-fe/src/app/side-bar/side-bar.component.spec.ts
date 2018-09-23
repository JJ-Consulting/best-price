import {async, ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';

import {SideBarComponent} from './side-bar.component';

import {MaterialModule}                from "../material.module";
import {BrowserAnimationsModule}       from "@angular/platform-browser/animations";
import {ReactiveFormsModule}           from "@angular/forms";
import {CampaignService, LoginService} from "@services";
import {Observable}                    from "rxjs/Observable";
import {from}                          from "rxjs/observable/from";
import {Campaign}                      from "@models";
import {By}                            from "@angular/platform-browser";
import {DebugElement}                  from "@angular/core";
import {Router}                        from "@angular/router";
import {MatDialog}                     from "@angular/material";

class MockLoginService {
  get isLoggedIn(): Observable<boolean> {
    return from([true]);
  }
}

class MockCampaignService {
  campaignLoaded = {subscribe: (callback: ((id: number) => {})) => callback(42)};
  getCampaigns(): Observable<Array<Campaign>> {
    return from([[
      {id: 1, name: 'Selling my car', price: null, startDate: new Date('2018-07-10T20:59:07+02:00'), endDate: null, currency: 'USD', interactions: []},
      {id: 2, name: 'Selling my bike', price: null, startDate: new Date('2018-07-10T20:59:07+02:00'), endDate: null, currency: 'EUR', interactions: []},
      {id: 3, name: 'finding a job', price: 42, startDate: new Date('2018-07-10T20:59:07+02:00'), endDate: new Date('2018-07-15T20:59:07+02:00'), currency: 'USD', interactions: []},
    ]]);
  }
}

class MockRouter {
  navigate(p: Array<any>): void {}
}

class MockMatDialog {
  open(): any {
    return {
      afterClosed: () => from([42])
    }
  }
}

describe('SideBarComponent', () => {
  let component: SideBarComponent;
  let fixture: ComponentFixture<SideBarComponent>;
  const mockCampaignService: MockCampaignService = new MockCampaignService();
  const mockRouter: MockRouter                   = new MockRouter();
  const mockMatDialog: MockMatDialog             = new MockMatDialog();

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SideBarComponent],
      providers:    [
        {provide: LoginService,    useClass: MockLoginService},
        {provide: CampaignService, useValue: mockCampaignService},
        {provide: Router,          useValue: mockRouter},
        {provide: MatDialog,       useValue: mockMatDialog}
      ],
      imports:      [ReactiveFormsModule, MaterialModule, BrowserAnimationsModule],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture   = TestBed.createComponent(SideBarComponent);
    component = fixture.componentInstance;
  });

  it('should print 2 opened campaigns and 1 closed campaigns', () => {
    // GIVEN
    spyOn(mockCampaignService, 'getCampaigns').and.callThrough();

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(mockCampaignService.getCampaigns).toHaveBeenCalled();
    const listItems: DebugElement[] = fixture.debugElement.queryAll(By.css('mat-list-item'));
    expect(listItems.length).toBe(4, 'There should be 4 items: 3 campaigns + 1 button');
  });

  it('should navigate to campaign 3', fakeAsync(() => {
    // GIVEN
    spyOn(mockRouter, 'navigate');

    // WHEN
    fixture.detectChanges();
    const lastItem: DebugElement = fixture.debugElement.queryAll(By.css('mat-list-item span')).pop();
    lastItem.triggerEventHandler('click', null);
    tick();

    // THEN
    expect(mockRouter.navigate).toHaveBeenCalledWith(['campaigns', 3]);
  }));

  it('should navigate to the newly created campaign', fakeAsync(() => {
    // GIVEN
    spyOn(mockRouter, 'navigate');

    // WHEN
    fixture.detectChanges();
    const addButton: DebugElement = fixture.debugElement.query(By.css('mat-list-item button'));
    addButton.triggerEventHandler('click', null);

    // THEN
    expect(mockRouter.navigate).toHaveBeenCalledWith(['campaigns', 42]);
  }));
});
