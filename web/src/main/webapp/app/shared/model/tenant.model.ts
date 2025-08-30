export interface ITenant {
  id?: number;
}

export class Tenant implements ITenant {
  constructor(public id?: number) {}
}
