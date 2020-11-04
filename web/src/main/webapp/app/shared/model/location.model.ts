import { IStore } from 'app/shared/model/store.model';
import { ITrack } from 'app/shared/model/track.model';

export interface ILocation {
  id?: number;
  lat?: string;
  lng?: string;
  stores?: IStore[];
  tracks?: ITrack[];
}

export class Location implements ILocation {
  constructor(public id?: number, public lat?: string, public lng?: string, public stores?: IStore[], public tracks?: ITrack[]) {}
}
