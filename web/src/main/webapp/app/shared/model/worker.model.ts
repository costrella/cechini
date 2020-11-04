import { Moment } from 'moment';
import { INote } from 'app/shared/model/note.model';
import { IStore } from 'app/shared/model/store.model';
import { IReport } from 'app/shared/model/report.model';
import { ITrack } from 'app/shared/model/track.model';
import { IManager } from 'app/shared/model/manager.model';

export interface IWorker {
  id?: number;
  name?: string;
  surname?: string;
  hiredDate?: Moment;
  desc?: string;
  phone?: string;
  login?: string;
  password?: string;
  target?: number;
  active?: boolean;
  notes?: INote[];
  stores?: IStore[];
  reports?: IReport[];
  tracks?: ITrack[];
  managers?: IManager[];
}

export class Worker implements IWorker {
  constructor(
    public id?: number,
    public name?: string,
    public surname?: string,
    public hiredDate?: Moment,
    public desc?: string,
    public phone?: string,
    public login?: string,
    public password?: string,
    public target?: number,
    public active?: boolean,
    public notes?: INote[],
    public stores?: IStore[],
    public reports?: IReport[],
    public tracks?: ITrack[],
    public managers?: IManager[]
  ) {
    this.active = this.active || false;
  }
}
