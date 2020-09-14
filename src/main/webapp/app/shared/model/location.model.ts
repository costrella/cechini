import { IStore } from 'app/shared/model/store.model';
import { IRoute } from 'app/shared/model/route.model';
import { IWarehouse } from 'app/shared/model/warehouse.model';

export interface ILocation {
  id?: number;
  street?: string;
  lat?: string;
  lng?: string;
  stores?: IStore[];
  routes?: IRoute[];
  warehouses?: IWarehouse[];
  cityId?: number;
}

export class Location implements ILocation {
  constructor(
    public id?: number,
    public street?: string,
    public lat?: string,
    public lng?: string,
    public stores?: IStore[],
    public routes?: IRoute[],
    public warehouses?: IWarehouse[],
    public cityId?: number
  ) {}
}
