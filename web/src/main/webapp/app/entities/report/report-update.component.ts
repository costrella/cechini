import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IReport, Report } from 'app/shared/model/report.model';
import { ReportService } from './report.service';
import { IOrder } from 'app/shared/model/order.model';
import { OrderService } from 'app/entities/order/order.service';
import { IWorker } from 'app/shared/model/worker.model';
import { WorkerService } from 'app/entities/worker/worker.service';
import { IStore } from 'app/shared/model/store.model';
import { StoreService } from 'app/entities/store/store.service';

type SelectableEntity = IOrder | IWorker | IStore;

@Component({
  selector: 'jhi-report-update',
  templateUrl: './report-update.component.html',
})
export class ReportUpdateComponent implements OnInit {
  isSaving = false;
  orders: IOrder[] = [];
  workers: IWorker[] = [];
  stores: IStore[] = [];

  editForm = this.fb.group({
    id: [],
    reportDate: [],
    desc: [null, [Validators.maxLength(2000)]],
    managerNote: [null, [Validators.maxLength(2000)]],
    orderId: [],
    workerId: [null, Validators.required],
    storeId: [null, Validators.required],
  });

  constructor(
    protected reportService: ReportService,
    protected orderService: OrderService,
    protected workerService: WorkerService,
    protected storeService: StoreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ report }) => {
      if (!report.id) {
        const today = moment().startOf('day');
        report.reportDate = today;
      }

      this.updateForm(report);

      // this.orderService
      //   .query({ filter: 'report-is-null' })
      //   .pipe(
      //     map((res: HttpResponse<IOrder[]>) => {
      //       return res.body || [];
      //     })
      //   )
      //   .subscribe((resBody: IOrder[]) => {
      //     if (!report.orderId) {
      //       this.orders = resBody;
      //     } else {
      //       this.orderService
      //         .find(report.orderId)
      //         .pipe(
      //           map((subRes: HttpResponse<IOrder>) => {
      //             return subRes.body ? [subRes.body].concat(resBody) : resBody;
      //           })
      //         )
      //         .subscribe((concatRes: IOrder[]) => (this.orders = concatRes));
      //     }
      //   });

      // this.workerService.query().subscribe((res: HttpResponse<IWorker[]>) => (this.workers = res.body || []));

      // this.storeService.query().subscribe((res: HttpResponse<IStore[]>) => (this.stores = res.body || []));
    });
  }

  updateForm(report: IReport): void {
    this.editForm.patchValue({
      id: report.id,
      reportDate: report.reportDate ? report.reportDate.format(DATE_TIME_FORMAT) : null,
      desc: report.desc,
      managerNote: report.managerNote,
      orderId: report.orderId,
      workerId: report.workerId,
      storeId: report.storeId,
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
      reportDate: this.editForm.get(['reportDate'])!.value ? moment(this.editForm.get(['reportDate'])!.value, DATE_TIME_FORMAT) : undefined,
      desc: this.editForm.get(['desc'])!.value,
      managerNote: this.editForm.get(['managerNote'])!.value,
      orderId: this.editForm.get(['orderId'])!.value,
      workerId: this.editForm.get(['workerId'])!.value,
      storeId: this.editForm.get(['storeId'])!.value,
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
