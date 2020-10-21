import { IOrder } from 'app/shared/model/order.model';

export interface IWarehouse {
  id?: number;
  name?: string;
  orders?: IOrder[];
}

export class Warehouse implements IWarehouse {
  constructor(public id?: number, public name?: string, public orders?: IOrder[]) {}
}
