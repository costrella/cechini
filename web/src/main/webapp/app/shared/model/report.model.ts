import { Moment } from 'moment';
import { IPhoto } from 'app/shared/model/photo.model';
import { INote } from 'app/shared/model/note.model';

export interface IReport {
  id?: number;
  reportDate?: Moment;
  desc?: string;
  managerNote?: string;
  orderId?: number;
  photos?: IPhoto[];
  notes?: INote[];
  workerSurname?: string;
  workerId?: number;
  storeName?: string;
  storeId?: number;
  photosCount?: number;
}

export class Report implements IReport {
  constructor(
    public id?: number,
    public reportDate?: Moment,
    public desc?: string,
    public managerNote?: string,
    public orderId?: number,
    public photos?: IPhoto[],
    public notes?: INote[],
    public workerSurname?: string,
    public workerId?: number,
    public storeName?: string,
    public storeId?: number,
    public photosCount?: number
  ) {}
}
