import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOrder, Order } from 'app/shared/model/order.model';
import { OrderService } from './order.service';
import { IWorker } from 'app/shared/model/worker.model';
import { WorkerService } from 'app/entities/worker/worker.service';
import { IStore } from 'app/shared/model/store.model';
import { StoreService } from 'app/entities/store/store.service';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status/status.service';
import { IWarehouse } from 'app/shared/model/warehouse.model';
import { WarehouseService } from 'app/entities/warehouse/warehouse.service';

type SelectableEntity = IWorker | IStore | IStatus | IWarehouse;

@Component({
  selector: 'jhi-order-update',
  templateUrl: './order-update.component.html',
})
export class OrderUpdateComponent implements OnInit {
  isSaving = false;
  workers: IWorker[] = [];
  stores: IStore[] = [];
  statuses: IStatus[] = [];
  warehouses: IWarehouse[] = [];
  orderDateDp: any;

  editForm = this.fb.group({
    id: [],
    orderDate: [null, [Validators.required]],
    workerId: [null, Validators.required],
    storeId: [null, Validators.required],
    statusId: [null, Validators.required],
    warehouseId: [],
  });

  constructor(
    protected orderService: OrderService,
    protected workerService: WorkerService,
    protected storeService: StoreService,
    protected statusService: StatusService,
    protected warehouseService: WarehouseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ order }) => {
      this.updateForm(order);

      this.workerService.query().subscribe((res: HttpResponse<IWorker[]>) => (this.workers = res.body || []));

      this.storeService.query().subscribe((res: HttpResponse<IStore[]>) => (this.stores = res.body || []));

      this.statusService.query().subscribe((res: HttpResponse<IStatus[]>) => (this.statuses = res.body || []));

      this.warehouseService.query().subscribe((res: HttpResponse<IWarehouse[]>) => (this.warehouses = res.body || []));
    });
  }

  updateForm(order: IOrder): void {
    this.editForm.patchValue({
      id: order.id,
      orderDate: order.orderDate,
      workerId: order.workerId,
      storeId: order.storeId,
      statusId: order.statusId,
      warehouseId: order.warehouseId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const order = this.createFromForm();
    if (order.id !== undefined) {
      this.subscribeToSaveResponse(this.orderService.update(order));
    } else {
      this.subscribeToSaveResponse(this.orderService.create(order));
    }
  }

  private createFromForm(): IOrder {
    return {
      ...new Order(),
      id: this.editForm.get(['id'])!.value,
      orderDate: this.editForm.get(['orderDate'])!.value,
      workerId: this.editForm.get(['workerId'])!.value,
      storeId: this.editForm.get(['storeId'])!.value,
      statusId: this.editForm.get(['statusId'])!.value,
      warehouseId: this.editForm.get(['warehouseId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrder>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
