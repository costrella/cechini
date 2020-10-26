export interface IPhoto {
  id?: number;
  uri?: string;
  valueContentType?: string;
  fileId?: number;
  reportId?: number;
}

export class Photo implements IPhoto {
  constructor(
    public id?: number,
    public uri?: string,
    public valueContentType?: string,
    public fileId?: number,
    public reportId?: number
  ) {}
}
