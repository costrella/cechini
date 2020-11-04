import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWarehouse } from 'app/shared/model/warehouse.model';
import { WarehouseService } from './warehouse.service';

@Component({
  templateUrl: './warehouse-delete-dialog.component.html',
})
export class WarehouseDeleteDialogComponent {
  warehouse?: IWarehouse;

  constructor(protected warehouseService: WarehouseService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.warehouseService.delete(id).subscribe(() => {
      this.eventManager.broadcast('warehouseListModification');
      this.activeModal.close();
    });
  }
}
