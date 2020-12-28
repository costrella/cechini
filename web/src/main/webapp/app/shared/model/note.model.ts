import { Moment } from 'moment';

export interface INote {
  id?: number;
  value?: string;
  date?: Moment;
  storeId?: number;
  workerNoteId?: number;
  managerNoteId?: number;
  reportId?: number;
}

export class Note implements INote {
  constructor(
    public id?: number,
    public value?: string,
    public date?: Moment,
    public storeId?: number,
    public workerNoteId?: number,
    public managerNoteId?: number,
    public reportId?: number
  ) {}
}
