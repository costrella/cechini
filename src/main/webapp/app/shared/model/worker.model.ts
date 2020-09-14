import { Moment } from 'moment';
import { IStore } from 'app/shared/model/store.model';
import { IOrder } from 'app/shared/model/order.model';
import { IReport } from 'app/shared/model/report.model';
import { IRoute } from 'app/shared/model/route.model';
import { IManager } from 'app/shared/model/manager.model';

export interface IWorker {
  id?: number;
  name?: string;
  surname?: string;
  hiredDate?: Moment;
  desc?: string;
  login?: string;
  password?: string;
  target?: number;
  stores?: IStore[];
  orders?: IOrder[];
  reports?: IReport[];
  routes?: IRoute[];
  statusName?: string;
  statusId?: number;
  managers?: IManager[];
}

export class Worker implements IWorker {
  constructor(
    public id?: number,
    public name?: string,
    public surname?: string,
    public hiredDate?: Moment,
    public desc?: string,
    public login?: string,
    public password?: string,
    public target?: number,
    public stores?: IStore[],
    public orders?: IOrder[],
    public reports?: IReport[],
    public routes?: IRoute[],
    public statusName?: string,
    public statusId?: number,
    public managers?: IManager[]
  ) {}
}
