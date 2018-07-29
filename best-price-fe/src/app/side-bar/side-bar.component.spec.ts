import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {SideBarComponent} from './side-bar.component';

import {MaterialModule}          from "../material.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ReactiveFormsModule}     from "@angular/forms";
import {LoginService}            from "@services";
import {Observable}              from "rxjs/Observable";
import {from}                    from "rxjs/observable/from";


describe('SideBarComponent', () => {
  let component: SideBarComponent;
  let fixture: ComponentFixture<SideBarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SideBarComponent],
      providers:    [
        {provide: LoginService, useClass: MockLoginService}
      ],
      imports:      [ReactiveFormsModule, MaterialModule, BrowserAnimationsModule],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture   = TestBed.createComponent(SideBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

class MockLoginService {
  get isLoggedIn(): Observable<boolean> {
    return from([true]);
  }
}
