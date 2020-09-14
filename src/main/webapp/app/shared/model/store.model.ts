import { IOrder } from 'app/shared/model/order.model';
import { IReport } from 'app/shared/model/report.model';

export interface IStore {
  id?: number;
  name?: string;
  orders?: IOrder[];
  reports?: IReport[];
  workerSurname?: string;
  workerId?: number;
  locationCity?: string;
  locationId?: number;
  storegroupName?: string;
  storegroupId?: number;
}

export class Store implements IStore {
  constructor(
    public id?: number,
    public name?: string,
    public orders?: IOrder[],
    public reports?: IReport[],
    public workerSurname?: string,
    public workerId?: number,
    public locationCity?: string,
    public locationId?: number,
    public storegroupName?: string,
    public storegroupId?: number
  ) {}
}
