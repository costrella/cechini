import { IStore } from 'app/shared/model/store.model';
import { ITrack } from 'app/shared/model/track.model';
import { IWarehouse } from 'app/shared/model/warehouse.model';

export interface ILocation {
  id?: number;
  street?: string;
  lat?: string;
  lng?: string;
  stores?: IStore[];
  tracks?: ITrack[];
  warehouses?: IWarehouse[];
  cityName?: string;
  cityId?: number;
}

export class Location implements ILocation {
  constructor(
    public id?: number,
    public street?: string,
    public lat?: string,
    public lng?: string,
    public stores?: IStore[],
    public tracks?: ITrack[],
    public warehouses?: IWarehouse[],
    public cityName?: string,
    public cityId?: number
  ) {}
}
