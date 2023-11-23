import { URLServiceService } from './../../services/urlservice.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {
  urlShort: string | any = "";
  urlLong: string = "";
  /*constructor(private newService: URLServiceService) { }
  ngOnInit():void{
    this.newService.shortUrlObservable.subscribe(res => {
      this.urlShort = res;
    })

    this.newService.longUrlObservable.subscribe(res =>{
      this.urlLong = res;
    })
  }*/
  setLongUrl(newLongUrl: string) {
    this.urlLong = newLongUrl;
  }

  setShortUrl(newShortUrl: string) {
    this.urlShort = newShortUrl;
  }
}
