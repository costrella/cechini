import { Moment } from 'moment';
import { IWorker } from 'app/shared/model/worker.model';

export interface IManager {
  id?: number;
  name?: string;
  surname?: string;
  hiredDate?: Moment;
  workers?: IWorker[];
}

export class Manager implements IManager {
  constructor(public id?: number, public name?: string, public surname?: string, public hiredDate?: Moment, public workers?: IWorker[]) {}
}
