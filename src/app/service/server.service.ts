import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { customResponse } from '../interface/custom-response';

@Injectable({
  providedIn: 'root'
})
export class ServerService {

  constructor(private http: HttpClient) { }

  getServers() : Observable<customResponse
}
