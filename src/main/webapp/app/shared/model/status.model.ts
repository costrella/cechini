import { IOrder } from 'app/shared/model/order.model';
import { IWorker } from 'app/shared/model/worker.model';

export interface IStatus {
  id?: number;
  name?: string;
  orders?: IOrder[];
  workers?: IWorker[];
}

export class Status implements IStatus {
  constructor(public id?: number, public name?: string, public orders?: IOrder[], public workers?: IWorker[]) {}
}
