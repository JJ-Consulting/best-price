import {TestBed, async, ComponentFixture} from '@angular/core/testing';
import {AppComponent}                     from './app.component';
import {Component}                        from "@angular/core";

describe('AppComponent', () => {
  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent,
        MockRouterOutletComponent,
        MockToolbarComponent,
        MockSidebarComponent
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture   = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the app', async(() => {
    expect(component).toBeTruthy();
  }));
});

@Component({
  selector: 'router-outlet',
  template: ''
})
class MockRouterOutletComponent {

}

@Component({
  selector: 'app-toolbar',
  template: ''
})
class MockToolbarComponent {}

@Component({
  selector: 'app-side-bar',
  template: ''
})
class MockSidebarComponent {}

