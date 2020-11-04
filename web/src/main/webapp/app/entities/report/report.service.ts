import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IReport } from 'app/shared/model/report.model';

type EntityResponseType = HttpResponse<IReport>;
type EntityArrayResponseType = HttpResponse<IReport[]>;

@Injectable({ providedIn: 'root' })
export class ReportService {
  public resourceUrl = SERVER_API_URL + 'api/reports';

  constructor(protected http: HttpClient) {}

  create(report: IReport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(report);
    return this.http
      .post<IReport>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(report: IReport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(report);
    return this.http
      .put<IReport>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IReport>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  findAllByStoreId(storeId: number, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IReport[]>(`${this.resourceUrl}/store/${storeId}`, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  findAllByWorkerId(workerId: number, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IReport[]>(`${this.resourceUrl}/worker/${workerId}`, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  findByStoreAndWorker(storeId: number, workerId: number, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IReport[]>(`${this.resourceUrl}/worker/${workerId}/store/${storeId}`, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IReport[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(report: IReport): IReport {
    const copy: IReport = Object.assign({}, report, {
      reportDate: report.reportDate && report.reportDate.isValid() ? report.reportDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.reportDate = res.body.reportDate ? moment(res.body.reportDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((report: IReport) => {
        report.reportDate = report.reportDate ? moment(report.reportDate) : undefined;
      });
    }
    return res;
  }
}
