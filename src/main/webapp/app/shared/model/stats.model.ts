export interface IStats {
  id?: number;
}

export class Stats implements IStats {
  constructor(public id?: number) {}
}
