import { IOrder } from 'app/shared/model/order.model';
import { OrderFileType } from 'app/shared/model/enumerations/order-file-type.model';

export interface IWarehouse {
  id?: number;
  name?: string;
  mail?: string;
  orderFileType?: OrderFileType;
  orders?: IOrder[];
}

export class Warehouse implements IWarehouse {
  constructor(
    public id?: number,
    public name?: string,
    public mail?: string,
    public orderFileType?: OrderFileType,
    public orders?: IOrder[]
  ) {}
}
