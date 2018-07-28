import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {SideBarComponent} from './side-bar.component';

import {MaterialModule}          from "../material.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ReactiveFormsModule}     from "@angular/forms";


describe('SideBarComponent', () => {
  let component: SideBarComponent;
  let fixture: ComponentFixture<SideBarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SideBarComponent],
      imports:      [ReactiveFormsModule, MaterialModule, BrowserAnimationsModule],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture   = TestBed.createComponent(SideBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
