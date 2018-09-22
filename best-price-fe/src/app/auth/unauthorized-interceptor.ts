import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from "@angular/common/http";

import {Observable}   from "rxjs/Observable";
import "rxjs/add/operator/do";
import {Injectable}   from "@angular/core";
import {LoginService} from "@services";

@Injectable()
export class UnauthorizedInterceptor implements HttpInterceptor {

  constructor(private loginService: LoginService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).do((event: HttpEvent<any>) => {
    }, (err: any) => {
      if (err instanceof HttpErrorResponse) {
        if (err.status === 401) {
          this.loginService.logout();
        }
      }
    });
  }

}
