import { IOrder } from 'app/shared/model/order.model';

export interface IWarehouse {
  id?: number;
  name?: string;
  orders?: IOrder[];
  locationId?: number;
}

export class Warehouse implements IWarehouse {
  constructor(public id?: number, public name?: string, public orders?: IOrder[], public locationId?: number) {}
}
