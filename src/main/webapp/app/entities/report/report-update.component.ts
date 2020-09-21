import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IReport, Report } from 'app/shared/model/report.model';
import { ReportService } from './report.service';
import { IWorker } from 'app/shared/model/worker.model';
import { WorkerService } from 'app/entities/worker/worker.service';
import { IStore } from 'app/shared/model/store.model';
import { StoreService } from 'app/entities/store/store.service';
import { IOrder } from 'app/shared/model/order.model';
import { OrderService } from 'app/entities/order/order.service';

type SelectableEntity = IWorker | IStore | IOrder;

@Component({
  selector: 'jhi-report-update',
  templateUrl: './report-update.component.html',
})
export class ReportUpdateComponent implements OnInit {
  isSaving = false;
  workers: IWorker[] = [];
  stores: IStore[] = [];
  orders: IOrder[] = [];
  reportDateDp: any;

  editForm = this.fb.group({
    id: [],
    number: [],
    reportDate: [],
    desc: [],
    workerId: [null, Validators.required],
    storeId: [null, Validators.required],
    orderId: [],
  });

  constructor(
    protected reportService: ReportService,
    protected workerService: WorkerService,
    protected storeService: StoreService,
    protected orderService: OrderService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ report }) => {
      this.updateForm(report);

      this.workerService.query().subscribe((res: HttpResponse<IWorker[]>) => (this.workers = res.body || []));

      this.storeService.query().subscribe((res: HttpResponse<IStore[]>) => (this.stores = res.body || []));

      this.orderService.query().subscribe((res: HttpResponse<IOrder[]>) => (this.orders = res.body || []));
    });
  }

  updateForm(report: IReport): void {
    this.editForm.patchValue({
      id: report.id,
      number: report.number,
      reportDate: report.reportDate,
      desc: report.desc,
      workerId: report.workerId,
      storeId: report.storeId,
      orderId: report.orderId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const report = this.createFromForm();
    if (report.id !== undefined) {
      this.subscribeToSaveResponse(this.reportService.update(report));
    } else {
      this.subscribeToSaveResponse(this.reportService.create(report));
    }
  }

  private createFromForm(): IReport {
    return {
      ...new Report(),
      id: this.editForm.get(['id'])!.value,
      number: this.editForm.get(['number'])!.value,
      reportDate: this.editForm.get(['reportDate'])!.value,
      desc: this.editForm.get(['desc'])!.value,
      workerId: this.editForm.get(['workerId'])!.value,
      storeId: this.editForm.get(['storeId'])!.value,
      orderId: this.editForm.get(['orderId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReport>>): void {
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
