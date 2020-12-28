import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStoreGroup } from 'app/shared/model/store-group.model';
import { StoreGroupService } from './store-group.service';

@Component({
  templateUrl: './store-group-delete-dialog.component.html',
})
export class StoreGroupDeleteDialogComponent {
  storeGroup?: IStoreGroup;

  constructor(
    protected storeGroupService: StoreGroupService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.storeGroupService.delete(id).subscribe(() => {
      this.eventManager.broadcast('storeGroupListModification');
      this.activeModal.close();
    });
  }
}
