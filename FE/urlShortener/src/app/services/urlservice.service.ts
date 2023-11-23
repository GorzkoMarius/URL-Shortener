import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { UrlModel } from '../model/url';

@Injectable({
  providedIn: 'root'
})
export class URLServiceService {
  URL_SHORTENER: string = 'http://localhost:8080/shorten';
  private sUrl: string | null = null;

  constructor(private httpClient: HttpClient) { }


 /**
  * The function `getShortUrlFromLongUrl` takes a `UrlModel` object representing a long URL and returns
  * an `Observable` that emits a string representing the corresponding short URL.
  * @param {UrlModel} longUrl - The `longUrl` parameter is of type `UrlModel`, which is an object
  * representing a long URL.
  * @returns an Observable of type string.
  */
  getShortUrlFromLongUrl(longUrl: UrlModel): Observable<{shortUrl: string}> {
    return this.httpClient.post<{shortUrl: string}>(this.URL_SHORTENER, longUrl);
  }
}
