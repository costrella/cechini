export interface ITrack {
  id?: number;
  name?: string;
  workerSurname?: string;
  workerId?: number;
  locationId?: number;
}

export class Track implements ITrack {
  constructor(
    public id?: number,
    public name?: string,
    public workerSurname?: string,
    public workerId?: number,
    public locationId?: number
  ) {}
}
