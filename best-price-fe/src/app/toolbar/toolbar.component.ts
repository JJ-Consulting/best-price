import { Component, OnInit } from '@angular/core';
import {LoginService}        from "@services";
import {Observable}          from "rxjs/Observable";

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {

  userLoggedIn$: Observable<boolean>;

  constructor(private loginService: LoginService) {
    this.userLoggedIn$ = this.loginService.isLoggedIn;
  }

  ngOnInit() {
  }

  onLogoutClick(): void {
    this.loginService.logout();
  }

}
