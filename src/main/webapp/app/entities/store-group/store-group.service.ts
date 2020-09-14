import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IStoreGroup } from 'app/shared/model/store-group.model';

type EntityResponseType = HttpResponse<IStoreGroup>;
type EntityArrayResponseType = HttpResponse<IStoreGroup[]>;

@Injectable({ providedIn: 'root' })
export class StoreGroupService {
  public resourceUrl = SERVER_API_URL + 'api/store-groups';

  constructor(protected http: HttpClient) {}

  create(storeGroup: IStoreGroup): Observable<EntityResponseType> {
    return this.http.post<IStoreGroup>(this.resourceUrl, storeGroup, { observe: 'response' });
  }

  update(storeGroup: IStoreGroup): Observable<EntityResponseType> {
    return this.http.put<IStoreGroup>(this.resourceUrl, storeGroup, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStoreGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStoreGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
