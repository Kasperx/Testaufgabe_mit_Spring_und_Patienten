import { Injectable } from '@angular/core';
import { HttpClient } from  '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  constructor(private http: HttpClient) {}

  // private configURL = 'localhost:8000';
  private configURL = 'https://my-json-server.typicode.com/JSGund/XHR-Fetch-Request-JavaScript/posts';

  getPosts(){
    return this.http.get(this.configURL);
  }
}