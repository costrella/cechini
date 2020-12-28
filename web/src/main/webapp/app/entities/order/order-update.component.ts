import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IOrder, Order } from 'app/shared/model/order.model';
import { OrderService } from './order.service';
import { IWarehouse } from 'app/shared/model/warehouse.model';
import { WarehouseService } from 'app/entities/warehouse/warehouse.service';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status/status.service';

type SelectableEntity = IWarehouse | IStatus;

@Component({
  selector: 'jhi-order-update',
  templateUrl: './order-update.component.html',
})
export class OrderUpdateComponent implements OnInit {
  isSaving = false;
  warehouses: IWarehouse[] = [];
  statuses: IStatus[] = [];

  editForm = this.fb.group({
    id: [],
    orderDate: [null, [Validators.required]],
    deliveryDate: [null, [Validators.required]],
    comment: [null, [Validators.maxLength(2000)]],
    warehouseId: [],
    statusId: [null, Validators.required],
  });

  constructor(
    protected orderService: OrderService,
    protected warehouseService: WarehouseService,
    protected statusService: StatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ order }) => {
      if (!order.id) {
        const today = moment().startOf('day');
        order.orderDate = today;
        order.deliveryDate = today;
      }

      this.updateForm(order);

      this.warehouseService.query().subscribe((res: HttpResponse<IWarehouse[]>) => (this.warehouses = res.body || []));

      this.statusService.query().subscribe((res: HttpResponse<IStatus[]>) => (this.statuses = res.body || []));
    });
  }

  updateForm(order: IOrder): void {
    this.editForm.patchValue({
      id: order.id,
      orderDate: order.orderDate ? order.orderDate.format(DATE_TIME_FORMAT) : null,
      deliveryDate: order.deliveryDate ? order.deliveryDate.format(DATE_TIME_FORMAT) : null,
      comment: order.comment,
      warehouseId: order.warehouseId,
      statusId: order.statusId,
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
      orderDate: this.editForm.get(['orderDate'])!.value ? moment(this.editForm.get(['orderDate'])!.value, DATE_TIME_FORMAT) : undefined,
      deliveryDate: this.editForm.get(['deliveryDate'])!.value
        ? moment(this.editForm.get(['deliveryDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      comment: this.editForm.get(['comment'])!.value,
      warehouseId: this.editForm.get(['warehouseId'])!.value,
      statusId: this.editForm.get(['statusId'])!.value,
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
