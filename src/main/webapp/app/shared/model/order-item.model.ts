export interface IOrderItem {
  id?: number;
  name?: string;
  quantity?: number;
  atr1?: string;
  atr2?: string;
  productName?: string;
  productId?: number;
  orderId?: number;
}

export class OrderItem implements IOrderItem {
  constructor(
    public id?: number,
    public name?: string,
    public quantity?: number,
    public atr1?: string,
    public atr2?: string,
    public productName?: string,
    public productId?: number,
    public orderId?: number
  ) {}
}
