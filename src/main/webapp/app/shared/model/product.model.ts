import { IOrderItem } from 'app/shared/model/order-item.model';

export interface IProduct {
  id?: number;
  name?: string;
  ean?: string;
  atr1?: string;
  atr2?: string;
  orderItems?: IOrderItem[];
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public name?: string,
    public ean?: string,
    public atr1?: string,
    public atr2?: string,
    public orderItems?: IOrderItem[]
  ) {}
}
