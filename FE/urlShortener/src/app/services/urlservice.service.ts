import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class URLServiceService {
  URL_SHORTENER: string = 'localhost:8080/shorten';
  private sUrl: string | null = null;
  /* `private shortUrlSub: BehaviorSubject<any> = new BehaviorSubject(null);` is creating an instance
  of the `BehaviorSubject` class and assigning it to the `shortUrlSub` property. */
  private shortUrlSub: BehaviorSubject<any> = new BehaviorSubject(null);
  /* `shortUrlObservable = this.shortUrlSub.asObservable();` is creating an observable
  `shortUrlObservable` that emits values from the `shortUrlSub` BehaviorSubject. This allows other
  components or services to subscribe to `shortUrlObservable` and receive updates whenever the value
  of `shortUrlSub` changes. */
  shortUrlObservable = this.shortUrlSub.asObservable();
  private lUrl: string | null = null;
  private longUrlSub: BehaviorSubject<any> = new BehaviorSubject(null);
  longUrlObservable = this.longUrlSub.asObservable();

  setSUrl(url: string) {
    this.sUrl = url;
    this.shortUrlSub.next(url);
  }

  getSUrl(): string | null {
    return this.sUrl;
  }

  setLUrl(url: string) {
    this.lUrl = url;
    this.longUrlSub.next(url);
  }

  getLUrl(): string | null {
    return this.lUrl;
  }

  clearUrls() {
    this.sUrl = null;
    this.shortUrlSub.next(null);
    this.lUrl = null;
    this.longUrlSub.next(null);
  }

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
