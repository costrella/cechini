import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IManager } from 'app/shared/model/manager.model';

type EntityResponseType = HttpResponse<IManager>;
type EntityArrayResponseType = HttpResponse<IManager[]>;

@Injectable({ providedIn: 'root' })
export class ManagerService {
  public resourceUrl = SERVER_API_URL + 'api/managers';

  constructor(protected http: HttpClient) {}

  create(manager: IManager): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(manager);
    return this.http
      .post<IManager>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(manager: IManager): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(manager);
    return this.http
      .put<IManager>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IManager>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IManager[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(manager: IManager): IManager {
    const copy: IManager = Object.assign({}, manager, {
      hiredDate: manager.hiredDate && manager.hiredDate.isValid() ? manager.hiredDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.hiredDate = res.body.hiredDate ? moment(res.body.hiredDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((manager: IManager) => {
        manager.hiredDate = manager.hiredDate ? moment(manager.hiredDate) : undefined;
      });
    }
    return res;
  }
}
