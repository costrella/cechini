import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPhotoFile } from 'app/shared/model/photo-file.model';

@Component({
  selector: 'jhi-photo-file-detail',
  templateUrl: './photo-file-detail.component.html',
})
export class PhotoFileDetailComponent implements OnInit {
  photoFile: IPhotoFile | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ photoFile }) => (this.photoFile = photoFile));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
