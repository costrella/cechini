import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPhotoFile } from 'app/shared/model/photo-file.model';
import { PhotoFileService } from './photo-file.service';
import { PhotoFileDeleteDialogComponent } from './photo-file-delete-dialog.component';

@Component({
  selector: 'jhi-photo-file',
  templateUrl: './photo-file.component.html',
})
export class PhotoFileComponent implements OnInit, OnDestroy {
  photoFiles?: IPhotoFile[];
  eventSubscriber?: Subscription;

  constructor(
    protected photoFileService: PhotoFileService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.photoFileService.query().subscribe((res: HttpResponse<IPhotoFile[]>) => (this.photoFiles = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPhotoFiles();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPhotoFile): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInPhotoFiles(): void {
    this.eventSubscriber = this.eventManager.subscribe('photoFileListModification', () => this.loadAll());
  }

  delete(photoFile: IPhotoFile): void {
    const modalRef = this.modalService.open(PhotoFileDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.photoFile = photoFile;
  }
}
