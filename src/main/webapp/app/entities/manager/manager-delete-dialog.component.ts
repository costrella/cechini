import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IManager } from 'app/shared/model/manager.model';
import { ManagerService } from './manager.service';

@Component({
  templateUrl: './manager-delete-dialog.component.html',
})
export class ManagerDeleteDialogComponent {
  manager?: IManager;

  constructor(protected managerService: ManagerService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.managerService.delete(id).subscribe(() => {
      this.eventManager.broadcast('managerListModification');
      this.activeModal.close();
    });
  }
}
