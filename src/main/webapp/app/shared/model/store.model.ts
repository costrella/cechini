import { INote } from 'app/shared/model/note.model';
import { IReport } from 'app/shared/model/report.model';

export interface IStore {
  id?: number;
  name?: string;
  nip?: string;
  desc?: string;
  visited?: boolean;
  notes?: INote[];
  reports?: IReport[];
  workerSurname?: string;
  workerId?: number;
  locationId?: number;
  storegroupName?: string;
  storegroupId?: number;
}

export class Store implements IStore {
  constructor(
    public id?: number,
    public name?: string,
    public nip?: string,
    public desc?: string,
    public visited?: boolean,
    public notes?: INote[],
    public reports?: IReport[],
    public workerSurname?: string,
    public workerId?: number,
    public locationId?: number,
    public storegroupName?: string,
    public storegroupId?: number
  ) {
    this.visited = this.visited || false;
  }
}
