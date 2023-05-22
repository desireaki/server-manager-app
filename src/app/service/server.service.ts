import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { customResponse } from '../interface/custom-response';
import { Observable, catchError, tap, throwError } from 'rxjs';
import { Server } from '../interface/server';
import { Status } from '../enum/status.enum';

@Injectable({
  providedIn: 'root'
})
export class ServerService {
  constructor(private http: HttpClient) { }

  // getServers() : Observable<customResponse> {
  //   return this.http.get<customResponse>(`http://localhost:8080/server/list`);
  // }
  private readonly apiUrl = 'http://localhost:8080'; 
  servers$ = <Observable<customResponse>>this.http.get<customResponse>(`${this.apiUrl}/server/list,`)
  .pipe(
    tap(console.log),
    catchError(this.handleError)
  );

  save$ = (server: Server) => <Observable<customResponse>>
  <Observable<customResponse>>this.http.post<customResponse>(`${this.apiUrl}/server/save`, server)
  .pipe(
    tap(console.log),
    catchError(this.handleError)
  );

  ping$ = (ipAddress: string) => <Observable<customResponse>>
  <Observable<customResponse>>this.http.get<customResponse>(`${this.apiUrl}/server/ping/${ipAddress}`)
  .pipe(
    tap(console.log),
    catchError(this.handleError)
  );

  filter$ = (status: Status, response: customResponse) => <Observable<customResponse>>
  new Observable<customResponse>(
    subscriber => {
      console.log(response);
      subscriber.next(
        status === Status.ALL ? { ...response, message: `Servers filtered by ${status} status`} :
        {
          ...response,

          message: response.data.servers
          .filter(server => server.status ===status).length > 0 ? `Servers filtered by 
          ${status === Status.SERVER_UP ? 'SERVER UP' : 'SERVER DOWN'} status` : `No servers of ${status} found`,

          data: {servers: response.data.servers
            .filter(server => server.status === status)}
        }
      )
    }
  )

  delete$ = (serverId: string) => <Observable<customResponse>>
  <Observable<customResponse>>this.http.delete<customResponse>(`${this.apiUrl}/server/delete/${serverId}`)
  .pipe(
    tap(console.log),
    catchError(this.handleError)
  );

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.log(error)
    return throwError(() => new Error(`Erro occured - Error code: ${error.status}`))
  }
}
