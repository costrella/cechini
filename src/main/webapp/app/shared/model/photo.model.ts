export interface IPhoto {
  id?: number;
  uri?: string;
  valueContentType?: string;
  value?: any;
  reportId?: number;
}

export class Photo implements IPhoto {
  constructor(public id?: number, public uri?: string, public valueContentType?: string, public value?: any, public reportId?: number) {}
}
