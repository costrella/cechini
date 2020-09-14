import { IStore } from 'app/shared/model/store.model';

export interface IStoreGroup {
  id?: number;
  name?: string;
  stores?: IStore[];
}

export class StoreGroup implements IStoreGroup {
  constructor(public id?: number, public name?: string, public stores?: IStore[]) {}
}
