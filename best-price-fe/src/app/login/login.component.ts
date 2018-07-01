import {Component, OnInit}                  from '@angular/core';
import {LoginService}                       from "./login.service";
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router}                             from "@angular/router";
import {MatSnackBar}                        from "@angular/material";

@Component({
  selector:    'app-login',
  templateUrl: './login.component.html',
  styleUrls:   ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  onLoginTab: boolean     = true;
  actionDisabled: boolean = false;
  loginForm: FormGroup;

  constructor(private loginService: LoginService, private fb: FormBuilder, private router: Router,
              private matSnackBar: MatSnackBar) {
    this.createForm();
  }

  ngOnInit() {
  }

  onLoginTabClick(): void {
    this.onLoginTab = true;

    this.loginForm.get('login').setValidators(Validators.required);
    this.loginForm.get('email').setValidators([]);
    this.loginForm.get('login').updateValueAndValidity();
    this.loginForm.get('email').updateValueAndValidity();
  }

  onCreateAccountTabClick(): void {
    this.onLoginTab = false;

    const login = this.loginForm.value.login;
    if (login && login.indexOf("@") > -1) {
      this.loginForm.controls['email'].setValue(login);
      this.loginForm.controls['login'].setValue('');
    }

    this.loginForm.get('email').setValidators([Validators.required, Validators.pattern("[^ @]*@[^ @]*")]);
    this.loginForm.get('login').setValidators([]);
    this.loginForm.get('login').updateValueAndValidity();
    this.loginForm.get('email').updateValueAndValidity();
  }

  private createForm(): void {
    this.loginForm = this.fb.group({
      email:    '',
      login:    ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  onSubmit(): void {
    this.actionDisabled = true;
    const formModel     = this.loginForm.value;

    if (this.onLoginTab) {
      this.loginService.login(formModel.login, formModel.password)
        .subscribe(() => {
            this.actionDisabled = false;
            this.router.navigate(['campaigns']);
          }, (error: any) => {
            this.actionDisabled = false;
            console.error(error);
          });
    } else {
      this.loginService.createUser(formModel.email, formModel.login, formModel.password)
        .subscribe(() => {
            this.actionDisabled = false;
            this.onLoginTabClick();
            this.onRevert();
          }, (error: any) => {
            this.actionDisabled = false;
            if (error.status === 409) {
              this.matSnackBar.open("User already exists with the provided email and / or login.");
            }
          });
    }
  }

  onRevert(): void {
    this.loginForm.reset();
    this.loginForm.markAsPristine();
    this.loginForm.markAsUntouched();
    this.loginForm.updateValueAndValidity();
  }
}
