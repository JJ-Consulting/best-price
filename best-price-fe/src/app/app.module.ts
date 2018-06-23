import {BrowserModule}           from '@angular/platform-browser';
import {NgModule}                from '@angular/core';
import {HttpClientModule}        from '@angular/common/http';
import {RouterModule}            from "@angular/router";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MaterialModule}          from "./material.module";
import {ReactiveFormsModule}     from "@angular/forms";

import {appRoutes} from "./routing.module";

import {AppComponent}       from './app.component';
import {LoginComponent}     from './login/login.component';
import {CampaignsComponent} from "./campaigns/campaigns.component";

import {LoginService}       from "./login/login.service";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    CampaignsComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes),
    BrowserAnimationsModule,
    MaterialModule,
    ReactiveFormsModule
  ],
  providers: [
    LoginService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
