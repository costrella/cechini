export interface IPhotoFile {
  id?: number;
  valueContentType?: string;
  value?: any;
  photoId?: number;
}

export class PhotoFile implements IPhotoFile {
  constructor(public id?: number, public valueContentType?: string, public value?: any, public photoId?: number) {}
}
