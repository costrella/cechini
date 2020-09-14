import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWorker, Worker } from 'app/shared/model/worker.model';
import { WorkerService } from './worker.service';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status/status.service';

@Component({
  selector: 'jhi-worker-update',
  templateUrl: './worker-update.component.html',
})
export class WorkerUpdateComponent implements OnInit {
  isSaving = false;
  statuses: IStatus[] = [];
  hiredDateDp: any;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    surname: [null, [Validators.required]],
    hiredDate: [],
    desc: [],
    login: [],
    password: [],
    target: [],
    statusId: [],
  });

  constructor(
    protected workerService: WorkerService,
    protected statusService: StatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ worker }) => {
      this.updateForm(worker);

      this.statusService.query().subscribe((res: HttpResponse<IStatus[]>) => (this.statuses = res.body || []));
    });
  }

  updateForm(worker: IWorker): void {
    this.editForm.patchValue({
      id: worker.id,
      name: worker.name,
      surname: worker.surname,
      hiredDate: worker.hiredDate,
      desc: worker.desc,
      login: worker.login,
      password: worker.password,
      target: worker.target,
      statusId: worker.statusId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const worker = this.createFromForm();
    if (worker.id !== undefined) {
      this.subscribeToSaveResponse(this.workerService.update(worker));
    } else {
      this.subscribeToSaveResponse(this.workerService.create(worker));
    }
  }

  private createFromForm(): IWorker {
    return {
      ...new Worker(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      surname: this.editForm.get(['surname'])!.value,
      hiredDate: this.editForm.get(['hiredDate'])!.value,
      desc: this.editForm.get(['desc'])!.value,
      login: this.editForm.get(['login'])!.value,
      password: this.editForm.get(['password'])!.value,
      target: this.editForm.get(['target'])!.value,
      statusId: this.editForm.get(['statusId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorker>>): void {
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

  trackById(index: number, item: IStatus): any {
    return item.id;
  }
}
