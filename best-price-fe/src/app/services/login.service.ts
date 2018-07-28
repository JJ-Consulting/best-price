import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs/Observable";

@Injectable()
export class LoginService {

  private static readonly USER_API_PATH: string = '/api/users/';

  constructor(private http: HttpClient) {
  }

  login(login: string, password: string): Observable<any> {
    return this.http.post(LoginService.USER_API_PATH + 'login', {login, password, email: 'foo@mail.com'})
  }

  createUser(email: string, login: string, password: string) {
    return this.http.post(LoginService.USER_API_PATH, {login, email, password});
  }
}
