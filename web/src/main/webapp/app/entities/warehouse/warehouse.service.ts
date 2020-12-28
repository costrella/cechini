import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWarehouse } from 'app/shared/model/warehouse.model';

type EntityResponseType = HttpResponse<IWarehouse>;
type EntityArrayResponseType = HttpResponse<IWarehouse[]>;

@Injectable({ providedIn: 'root' })
export class WarehouseService {
  public resourceUrl = SERVER_API_URL + 'api/warehouses';

  constructor(protected http: HttpClient) {}

  create(warehouse: IWarehouse): Observable<EntityResponseType> {
    return this.http.post<IWarehouse>(this.resourceUrl, warehouse, { observe: 'response' });
  }

  update(warehouse: IWarehouse): Observable<EntityResponseType> {
    return this.http.put<IWarehouse>(this.resourceUrl, warehouse, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWarehouse>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWarehouse[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
