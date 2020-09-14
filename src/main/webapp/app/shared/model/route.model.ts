export interface IRoute {
  id?: number;
  name?: string;
  workerSurname?: string;
  workerId?: number;
  locationId?: number;
}

export class Route implements IRoute {
  constructor(
    public id?: number,
    public name?: string,
    public workerSurname?: string,
    public workerId?: number,
    public locationId?: number
  ) {}
}
