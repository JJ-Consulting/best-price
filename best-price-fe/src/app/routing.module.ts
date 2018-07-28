import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {CampaignsComponent} from "./campaigns/campaigns.component";

const appRoutes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'campaigns/:id', component: CampaignsComponent},
  {path: '',
    redirectTo: '/campaigns/last',
    pathMatch: 'full'
  },
  // { path: '**', component: PageNotFoundComponent }
];

export {appRoutes};
