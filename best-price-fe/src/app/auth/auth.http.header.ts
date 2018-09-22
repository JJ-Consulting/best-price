import {
  HttpEvent,
  HttpHandler, HttpHeaders,
  HttpInterceptor,
  HttpRequest
} from "@angular/common/http";

import {Observable} from "rxjs/Observable";


export class AuthHttpHeader implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Clone the request to add the new header
    const headers: HttpHeaders = req.headers.set('Authorization', 'Bearer ' + localStorage.getItem('best-price-token'));
    const clonedRequest = req.clone({headers});

    // Pass the cloned request instead of the original request to the next handle
    return next.handle(clonedRequest);
  }

}
