import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IStats } from 'app/shared/model/stats.model';
import { IChart01 } from 'app/shared/model/chart01.model';

type EntityResponseType = HttpResponse<IStats>;
type EntityArrayResponseType = HttpResponse<IStats[]>;
type EntityResponseTypeChart = HttpResponse<IChart01>;

@Injectable({ providedIn: 'root' })
export class StatsService {
  public resourceUrl = SERVER_API_URL + 'api/stats';

  constructor(protected http: HttpClient) {}

  chart01(): Observable<EntityResponseTypeChart> {
    return this.http.get<IChart01>(`${this.resourceUrl}/chart01`, { observe: 'response' });
  }

  chart02(): Observable<EntityResponseTypeChart> {
    return this.http.get<IChart01>(`${this.resourceUrl}/chart02`, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStats>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStats[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
}
