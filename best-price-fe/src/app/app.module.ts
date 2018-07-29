import {BrowserModule}                       from '@angular/platform-browser';
import {NgModule}                            from '@angular/core';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {RouterModule}                        from "@angular/router";
import {BrowserAnimationsModule}             from "@angular/platform-browser/animations";
import {MaterialModule}                      from "./material.module";
import {ReactiveFormsModule}                 from "@angular/forms";

import {appRoutes} from "./routing.module";

import {AppComponent}       from './app.component';
import {LoginComponent}     from './login/login.component';
import {CampaignsComponent} from "./campaigns/campaigns.component";

import {AuthHttpHeader}                from "./auth.http.header";
import {CampaignService, LoginService} from "@services";
import {SideBarComponent}              from './side-bar/side-bar.component';
import {ToolbarComponent}              from './toolbar/toolbar.component';
import {AuthGuard}                     from "./auth-guard";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    CampaignsComponent,
    SideBarComponent,
    ToolbarComponent
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
    {
      provide:  HTTP_INTERCEPTORS,
      useClass: AuthHttpHeader,
      multi:    true,
    },
    AuthGuard,
    LoginService,
    CampaignService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
