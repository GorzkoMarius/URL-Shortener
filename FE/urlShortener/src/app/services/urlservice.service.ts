import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class URLServiceService {
  URL_SHORTENER: string = 'localhost:8080/shorten';

  constructor(private httpClient: HttpClient) { }

  /**
   * The function `getShortUrlFromLongUrl` takes a long URL as input and returns an Observable that
   * makes a POST request to a URL shortener API, passing the long URL as the request body.
   * @param {string} longUrl - A string representing the long URL that you want to shorten.
   * @returns an Observable of type `any`.
   */
  getShortUrlFromLongUrl(longUrl: string): Observable<any> {
    return this.httpClient.post<string>(this.URL_SHORTENER, longUrl);
  }
}
