import {Component, OnInit} from '@angular/core';
import {HttpClient}        from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  message = 'loading...';

  constructor (private http: HttpClient) {}

  ngOnInit(): void {
    this.http.get('/api/hello')
      .subscribe((data: any) => {
        this.message = data.msg;
      }, (error: any) => {
        console.log('error', error);
      })
  }

}
