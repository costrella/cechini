import { Moment } from 'moment';
import { INote } from 'app/shared/model/note.model';
import { IWorker } from 'app/shared/model/worker.model';

export interface IManager {
  id?: number;
  name?: string;
  surname?: string;
  hiredDate?: Moment;
  notes?: INote[];
  workers?: IWorker[];
}

export class Manager implements IManager {
  constructor(
    public id?: number,
    public name?: string,
    public surname?: string,
    public hiredDate?: Moment,
    public notes?: INote[],
    public workers?: IWorker[]
  ) {}
}
