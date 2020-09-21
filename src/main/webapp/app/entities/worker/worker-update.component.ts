import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWorker, Worker } from 'app/shared/model/worker.model';
import { WorkerService } from './worker.service';

@Component({
  selector: 'jhi-worker-update',
  templateUrl: './worker-update.component.html',
})
export class WorkerUpdateComponent implements OnInit {
  isSaving = false;
  hiredDateDp: any;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    surname: [null, [Validators.required]],
    hiredDate: [],
    desc: [null, [Validators.maxLength(2000)]],
    phone: [],
    login: [],
    password: [],
    target: [],
    active: [],
  });

  constructor(protected workerService: WorkerService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ worker }) => {
      this.updateForm(worker);
    });
  }

  updateForm(worker: IWorker): void {
    this.editForm.patchValue({
      id: worker.id,
      name: worker.name,
      surname: worker.surname,
      hiredDate: worker.hiredDate,
      desc: worker.desc,
      phone: worker.phone,
      login: worker.login,
      password: worker.password,
      target: worker.target,
      active: worker.active,
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
      phone: this.editForm.get(['phone'])!.value,
      login: this.editForm.get(['login'])!.value,
      password: this.editForm.get(['password'])!.value,
      target: this.editForm.get(['target'])!.value,
      active: this.editForm.get(['active'])!.value,
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
}
