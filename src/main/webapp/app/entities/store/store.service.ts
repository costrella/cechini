import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { IChart01 } from 'app/shared/model/chart01.model';
import { IStore } from 'app/shared/model/store.model';
import { createRequestOption } from 'app/shared/util/request-util';
import { Observable } from 'rxjs';

type EntityResponseType = HttpResponse<IStore>;
type EntityResponseTypeChart = HttpResponse<IChart01>;

type EntityArrayResponseType = HttpResponse<IStore[]>;

@Injectable({ providedIn: 'root' })
export class StoreService {
  public resourceUrl = SERVER_API_URL + 'api/stores';

  constructor(protected http: HttpClient) {}

  create(store: IStore): Observable<EntityResponseType> {
    return this.http.post<IStore>(this.resourceUrl, store, { observe: 'response' });
  }

  update(store: IStore): Observable<EntityResponseType> {
    return this.http.put<IStore>(this.resourceUrl, store, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStore>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  chart01(): Observable<EntityResponseTypeChart> {
    return this.http.get<IChart01>(`${this.resourceUrl}/chart01`, { observe: 'response' });
  }

  findAllByWorker(workerId: number, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStore[]>(`${this.resourceUrl}/worker/${workerId}`, { params: options, observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStore[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findAll(): Observable<EntityArrayResponseType> {
    return this.http.get<IStore[]>(`${this.resourceUrl}/all`, { observe: 'response' });
  }
}
