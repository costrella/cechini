import { IOrderItem } from 'app/shared/model/order-item.model';

export interface IProduct {
  id?: number;
  name?: string;
  capacity?: number;
  eanArt?: string;
  eanPack?: string;
  packCountPalette?: number;
  artCountPalette?: number;
  layerCountPalette?: number;
  orderItems?: IOrderItem[];
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public name?: string,
    public capacity?: number,
    public eanArt?: string,
    public eanPack?: string,
    public packCountPalette?: number,
    public artCountPalette?: number,
    public layerCountPalette?: number,
    public orderItems?: IOrderItem[]
  ) {}
}
