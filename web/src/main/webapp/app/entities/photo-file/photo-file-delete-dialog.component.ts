import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPhotoFile } from 'app/shared/model/photo-file.model';
import { PhotoFileService } from './photo-file.service';

@Component({
  templateUrl: './photo-file-delete-dialog.component.html',
})
export class PhotoFileDeleteDialogComponent {
  photoFile?: IPhotoFile;

  constructor(protected photoFileService: PhotoFileService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.photoFileService.delete(id).subscribe(() => {
      this.eventManager.broadcast('photoFileListModification');
      this.activeModal.close();
    });
  }
}
