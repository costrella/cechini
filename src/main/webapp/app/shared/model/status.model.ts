import { IOrder } from 'app/shared/model/order.model';

export interface IStatus {
  id?: number;
  name?: string;
  orders?: IOrder[];
}

export class Status implements IStatus {
  constructor(public id?: number, public name?: string, public orders?: IOrder[]) {}
}
