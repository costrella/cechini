import { Moment } from 'moment';
import { IOrderItem } from 'app/shared/model/order-item.model';

export interface IOrder {
  id?: number;
  orderDate?: Moment;
  deliveryDate?: Moment;
  comment?: string;
  orderItems?: IOrderItem[];
  reportId?: number;
  warehouseName?: string;
  warehouseId?: number;
  statusName?: string;
  statusId?: number;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public orderDate?: Moment,
    public deliveryDate?: Moment,
    public comment?: string,
    public orderItems?: IOrderItem[],
    public reportId?: number,
    public warehouseName?: string,
    public warehouseId?: number,
    public statusName?: string,
    public statusId?: number
  ) {}
}
