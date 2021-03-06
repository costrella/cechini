import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWorker } from 'app/shared/model/worker.model';
import { IChart01 } from 'app/shared/model/chart01.model';

type EntityResponseType = HttpResponse<IWorker>;
type EntityArrayResponseType = HttpResponse<IWorker[]>;
type EntityResponseTypeChart = HttpResponse<IChart01>;

@Injectable({ providedIn: 'root' })
export class WorkerService {
  public resourceUrl = SERVER_API_URL + 'api/workers';

  constructor(protected http: HttpClient) {}

  chart01(): Observable<EntityResponseTypeChart> {
    return this.http.get<IChart01>(`${this.resourceUrl}/chart01`, { observe: 'response' });
  }

  chart02(): Observable<EntityResponseTypeChart> {
    return this.http.get<IChart01>(`${this.resourceUrl}/chart02`, { observe: 'response' });
  }

  create(worker: IWorker): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(worker);
    return this.http
      .post<IWorker>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(worker: IWorker): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(worker);
    return this.http
      .put<IWorker>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IWorker>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  findAll(): Observable<EntityArrayResponseType> {
    return this.http
      .get<IWorker[]>(`${this.resourceUrl}/all`, { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IWorker[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(worker: IWorker): IWorker {
    const copy: IWorker = Object.assign({}, worker, {
      hiredDate: worker.hiredDate && worker.hiredDate.isValid() ? worker.hiredDate.toJSON() : undefined,
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
      res.body.forEach((worker: IWorker) => {
        worker.hiredDate = worker.hiredDate ? moment(worker.hiredDate) : undefined;
      });
    }
    return res;
  }
}
