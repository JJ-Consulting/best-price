import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {CampaignsComponent} from "./campaigns/campaigns.component";

const appRoutes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'campaigns', component: CampaignsComponent},
  {path: '',
    redirectTo: '/campaigns',
    pathMatch: 'full'
  },
  // { path: '**', component: PageNotFoundComponent }
];

export {appRoutes};
