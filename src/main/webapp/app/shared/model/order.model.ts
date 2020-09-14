import { Moment } from 'moment';
import { IOrderItem } from 'app/shared/model/order-item.model';

export interface IOrder {
  id?: number;
  orderDate?: Moment;
  orderItems?: IOrderItem[];
  workerSurname?: string;
  workerId?: number;
  storeName?: string;
  storeId?: number;
  statusName?: string;
  statusId?: number;
  warehouseName?: string;
  warehouseId?: number;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public orderDate?: Moment,
    public orderItems?: IOrderItem[],
    public workerSurname?: string,
    public workerId?: number,
    public storeName?: string,
    public storeId?: number,
    public statusName?: string,
    public statusId?: number,
    public warehouseName?: string,
    public warehouseId?: number
  ) {}
}
