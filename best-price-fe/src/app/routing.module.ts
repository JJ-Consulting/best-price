import {Routes}             from '@angular/router';
import {LoginComponent}     from "./login/login.component";
import {CampaignsComponent} from "./campaigns/campaigns.component";
import {AuthGuard}          from "./auth-guard";

const appRoutes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'campaigns/:id', component: CampaignsComponent, canActivate: [AuthGuard]},
  {
    path:       '',
    redirectTo: '/campaigns/last',
    pathMatch:  'full'
  },
  // { path: '**', component: PageNotFoundComponent }
];

export {appRoutes};
