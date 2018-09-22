import {Routes}                from '@angular/router';
import {LoginComponent}        from "./login/login.component";
import {AuthGuard}             from "./auth/auth-guard";
import {InteractionsComponent} from "./interactions/interactions.component";

const appRoutes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'campaigns/:id', component: InteractionsComponent, canActivate: [AuthGuard]},
  {
    path:       '',
    redirectTo: '/campaigns/last',
    pathMatch:  'full'
  },
  // { path: '**', component: PageNotFoundComponent }
];

export {appRoutes};
