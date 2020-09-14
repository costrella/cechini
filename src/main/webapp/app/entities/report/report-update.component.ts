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

type SelectableEntity = IWorker | IStore;

@Component({
  selector: 'jhi-report-update',
  templateUrl: './report-update.component.html',
})
export class ReportUpdateComponent implements OnInit {
  isSaving = false;
  workers: IWorker[] = [];
  stores: IStore[] = [];
  reportDateDp: any;

  editForm = this.fb.group({
    id: [],
    number: [],
    reportDate: [],
    workerDesc: [],
    managerDesc: [],
    workerId: [],
    storeId: [],
  });

  constructor(
    protected reportService: ReportService,
    protected workerService: WorkerService,
    protected storeService: StoreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ report }) => {
      this.updateForm(report);

      this.workerService.query().subscribe((res: HttpResponse<IWorker[]>) => (this.workers = res.body || []));

      this.storeService.query().subscribe((res: HttpResponse<IStore[]>) => (this.stores = res.body || []));
    });
  }

  updateForm(report: IReport): void {
    this.editForm.patchValue({
      id: report.id,
      number: report.number,
      reportDate: report.reportDate,
      workerDesc: report.workerDesc,
      managerDesc: report.managerDesc,
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
      number: this.editForm.get(['number'])!.value,
      reportDate: this.editForm.get(['reportDate'])!.value,
      workerDesc: this.editForm.get(['workerDesc'])!.value,
      managerDesc: this.editForm.get(['managerDesc'])!.value,
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
