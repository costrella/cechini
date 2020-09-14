export interface IOrderItem {
  id?: number;
  name?: string;
  quantity?: number;
  productName?: string;
  productId?: number;
  orderId?: number;
}

export class OrderItem implements IOrderItem {
  constructor(
    public id?: number,
    public name?: string,
    public quantity?: number,
    public productName?: string,
    public productId?: number,
    public orderId?: number
  ) {}
}
