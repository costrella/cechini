import { ILocation } from 'app/shared/model/location.model';

export interface ICity {
  id?: number;
  name?: string;
  locations?: ILocation[];
}

export class City implements ICity {
  constructor(public id?: number, public name?: string, public locations?: ILocation[]) {}
}
