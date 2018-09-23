import {Injectable}      from '@angular/core';
import {HttpClient}      from '@angular/common/http';
import {Observable}      from "rxjs/Observable";
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {share}           from "rxjs/operators";
import {SimpleMessage}   from "@models";
import {Router}          from "@angular/router";
import {ErrorObservable} from "rxjs/observable/ErrorObservable";

@Injectable()
export class LoginService {

  static readonly USER_API_PATH: string = '/api/users/';

  private loggedIn: BehaviorSubject<boolean>;

  constructor(private http: HttpClient, private router: Router) {
    if (localStorage.getItem('best-price-token')) {
      this.loggedIn = new BehaviorSubject<boolean>(true);
    } else {
      this.loggedIn = new BehaviorSubject<boolean>(false);
    }
  }

  get isLoggedIn(): Observable<boolean> {
    return this.loggedIn.asObservable();
  }

  login(login: string, password: string): Observable<SimpleMessage> {
    const result: Observable<SimpleMessage> = this.http
      .post<SimpleMessage>(LoginService.USER_API_PATH + 'login', {login, password})
      .pipe(share());

    result.subscribe((result: SimpleMessage) => {
      localStorage.setItem('best-price-token', result.message);
      this.loggedIn.next(true);
    }, (error: any) => new ErrorObservable(error));

    return result;
  }

  logout(): void {
    localStorage.removeItem("best-price-token");
    this.loggedIn.next(false);
    this.router.navigate(['login']);
  }

  createUser(email: string, login: string, password: string): Observable<any> {
    return this.http.post(LoginService.USER_API_PATH, {login, email, password});
  }
}
