import { Moment } from 'moment';
import { INote } from 'app/shared/model/note.model';
import { IPhotoFile } from './photo-file.model';

export interface IReport {
  id?: number;
  reportDate?: Moment;
  desc?: string;
  managerNote?: string;
  orderId?: number;
  photos?: IPhotoFile[];
  notes?: INote[];
  workerSurname?: string;
  workerId?: number;
  storeName?: string;
  storeId?: number;
  photosCount?: number;
  readByWorker?: boolean;
  readByManager?: boolean;
}

export class Report implements IReport {
  constructor(
    public id?: number,
    public reportDate?: Moment,
    public desc?: string,
    public managerNote?: string,
    public orderId?: number,
    public photos?: IPhotoFile[],
    public notes?: INote[],
    public workerSurname?: string,
    public workerId?: number,
    public storeName?: string,
    public storeId?: number,
    public photosCount?: number
  ) {}
}
