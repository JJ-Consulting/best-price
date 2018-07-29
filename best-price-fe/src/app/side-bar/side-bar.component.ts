import {Component, OnInit} from '@angular/core';
import {Observable}        from "rxjs/Observable";
import {LoginService}      from "@services";

@Component({
  selector:    'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls:   ['./side-bar.component.scss']
})
export class SideBarComponent implements OnInit {

  userLoggedIn$: Observable<boolean>;

  constructor(private loginService: LoginService) {
    this.userLoggedIn$ = this.loginService.isLoggedIn;
  }

  ngOnInit() {
  }

}
