import {LoginService}                  from "@services";
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {getTestBed, TestBed}                            from "@angular/core/testing";
import {AuthHttpHeader}                                 from "../auth.http.header";
import {HTTP_INTERCEPTORS, HttpRequest}                 from "@angular/common/http";
import {Router}                                         from "@angular/router";


describe('LoginService', () => {
  let injector: TestBed;
  let service: LoginService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        {
          provide: HTTP_INTERCEPTORS,
          useClass: AuthHttpHeader,
          multi: true,
        },
        {provide: Router, useClass: MockRouter},
        LoginService
      ],
    });

    localStorage.removeItem('best-price-token');
    injector = getTestBed();
    service  = injector.get(LoginService);
    httpMock = injector.get(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should emit event on user login', () => {
    // THEN
    let expectedResult = false;
    // First false is emit as the default value, then true is emit after login
    service.isLoggedIn.subscribe((result: boolean) => {
      expect(result).toBe(expectedResult);
      expectedResult = !expectedResult;
    });

    // WHEN
    service.login('', '');

    // GIVEN
    httpMock.expectOne((request: HttpRequest<any>) => {
      return request.method == 'POST'
        && request.url === LoginService.USER_API_PATH + 'login';
    }).flush({});
  });

  it('should emit event on user logout', () => {
    // THEN
    let expectedResult = false;
    // First false is emit as the default value, then false is emit after logout
    service.isLoggedIn.subscribe((result: boolean) => expect(result).toBeFalsy());

    // WHEN
    service.logout();
  });

  it('should store token on login', () => {
    // WHEN
    service.login('foo', 'bar').subscribe(() =>
      // THEN
      expect(localStorage.getItem('best-price-token')).toBe('42'));

    // GIVEN
    localStorage.removeItem('best-price-token');
    httpMock.expectOne((request: HttpRequest<any>) => {
      return request.method == 'POST'
        && request.url === LoginService.USER_API_PATH + 'login';
    }).flush({message: '42'});
  });

  it('should remove the token on logout', () => {
    // GIVEN
    localStorage.setItem('best-price-token', '42');

    // WHEN
    service.logout();

    // THEN
    expect(localStorage.getItem('best-price-token')).toBeFalsy();
  });
});

class MockRouter {
  navigate(): void {
  }
}
