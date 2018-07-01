import {
  async,
  ComponentFixture,
  fakeAsync,
  TestBed,
  tick
} from '@angular/core/testing';
import {ReactiveFormsModule}     from "@angular/forms";
import {MaterialModule}          from "../material.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

import {LoginComponent} from './login.component';
import {LoginService}   from "./login.service";
import {Observable}     from "rxjs/Observable";
import {from}           from "rxjs/observable/from";
import {Router}         from "@angular/router";
import {By}             from "@angular/platform-browser";

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports:      [ReactiveFormsModule, MaterialModule, BrowserAnimationsModule],
      declarations: [LoginComponent],
      providers:    [
        {provide: LoginService, useClass: MockLoginService},
        {provide: Router, useClass: MockRouter}
      ]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture   = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  describe("Tab switching", () => {

    const clickOnButtonWithId = (id: string) => {
      let button = fixture.debugElement.query(By.css('#' + id));
      button.triggerEventHandler('click', null);
      tick(); // simulates the passage of time until all pending asynchronous activities finish
      fixture.detectChanges();
    };

    it('should switch between tabs', fakeAsync(() => {
      expect(component.onLoginTab).toBeTruthy();

      clickOnButtonWithId("createAccountTabButton");
      expect(component.onLoginTab).toBeFalsy();

      clickOnButtonWithId("loginTabButton");
      expect(component.onLoginTab).toBeTruthy();
    }));

    it('should go to CREATE ACCOUNT tab copying login', fakeAsync(() => {
      // GIVEN
      let login = component.loginForm.controls['login'];
      login.setValue('foobar');

      // WHEN
      clickOnButtonWithId('createAccountTabButton');

      // THEN
      expect(component.loginForm.value.login).toBe('foobar');
    }));

    it('should go to CREATE ACCOUNT tab copying login to email field', fakeAsync(() => {
      // GIVEN
      let login = component.loginForm.controls['login'];
      login.setValue('foobar@mail.com');

      // WHEN
      clickOnButtonWithId('createAccountTabButton');

      // THEN
      expect(component.loginForm.value.login).toBe('');
      expect(component.loginForm.value.email).toBe('foobar@mail.com');
    }));
  });

});


class MockLoginService {
  login(): Observable<object> {
    return from([{}]);
  }

  createUser(): Observable<object> {
    return from([{}]);
  }
}

class MockRouter {
  navigate(): void {
  }
}
