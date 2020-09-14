import { IOrderItem } from 'app/shared/model/order-item.model';

export interface IProduct {
  id?: number;
  name?: string;
  orderItems?: IOrderItem[];
}

export class Product implements IProduct {
  constructor(public id?: number, public name?: string, public orderItems?: IOrderItem[]) {}
}
