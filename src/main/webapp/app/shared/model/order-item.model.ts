export interface IOrderItem {
  id?: number;
  artCount?: number;
  packCount?: number;
  productName?: string;
  productId?: number;
  orderId?: number;
}

export class OrderItem implements IOrderItem {
  constructor(
    public id?: number,
    public artCount?: number,
    public packCount?: number,
    public productName?: string,
    public productId?: number,
    public orderId?: number
  ) {}
}
