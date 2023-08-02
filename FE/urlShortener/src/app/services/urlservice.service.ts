import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class URLServiceService {
  URL_SHORTENER: string = 'localhost:8080/shorten';

  constructor(private httpClient: HttpClient) { }

  getShortUrlFromLongUrl(longUrl: string): Observable<any> {
    return this.httpClient.post<string>(this.URL_SHORTENER, longUrl);
  }
}
