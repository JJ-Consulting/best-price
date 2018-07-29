import {
  async,
  ComponentFixture,
  TestBed,
} from '@angular/core/testing';

import {ToolbarComponent}        from './toolbar.component';
import {MaterialModule}          from "../material.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ReactiveFormsModule}     from "@angular/forms";
import {LoginService}            from "@services";

class MockLoginService {
  logout(): void {}
}


describe('ToolbarComponent', () => {
  let component: ToolbarComponent;
  let fixture: ComponentFixture<ToolbarComponent>;
  const mockLoginService = new MockLoginService();

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ToolbarComponent],
      providers: [
        {provide: LoginService, useValue: mockLoginService}
      ],
      imports:      [ReactiveFormsModule, MaterialModule, BrowserAnimationsModule],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture   = TestBed.createComponent(ToolbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should logout', () => {
    // GIVEN
    spyOn(mockLoginService, 'logout');

    // WHEN
    component.onLogoutClick();

    // THEN
    expect(mockLoginService.logout).toHaveBeenCalled();
  });
});

