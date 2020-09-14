import { Moment } from 'moment';
import { IPhoto } from 'app/shared/model/photo.model';

export interface IReport {
  id?: number;
  number?: string;
  reportDate?: Moment;
  workerDesc?: string;
  managerDesc?: string;
  photos?: IPhoto[];
  workerSurname?: string;
  workerId?: number;
  storeName?: string;
  storeId?: number;
}

export class Report implements IReport {
  constructor(
    public id?: number,
    public number?: string,
    public reportDate?: Moment,
    public workerDesc?: string,
    public managerDesc?: string,
    public photos?: IPhoto[],
    public workerSurname?: string,
    public workerId?: number,
    public storeName?: string,
    public storeId?: number
  ) {}
}
