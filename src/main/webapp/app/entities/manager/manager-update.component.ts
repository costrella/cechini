import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IManager, Manager } from 'app/shared/model/manager.model';
import { ManagerService } from './manager.service';
import { IWorker } from 'app/shared/model/worker.model';
import { WorkerService } from 'app/entities/worker/worker.service';

@Component({
  selector: 'jhi-manager-update',
  templateUrl: './manager-update.component.html',
})
export class ManagerUpdateComponent implements OnInit {
  isSaving = false;
  workers: IWorker[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    surname: [null, [Validators.required]],
    hiredDate: [],
    workers: [],
  });

  constructor(
    protected managerService: ManagerService,
    protected workerService: WorkerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ manager }) => {
      if (!manager.id) {
        const today = moment().startOf('day');
        manager.hiredDate = today;
      }

      this.updateForm(manager);

      this.workerService.query().subscribe((res: HttpResponse<IWorker[]>) => (this.workers = res.body || []));
    });
  }

  updateForm(manager: IManager): void {
    this.editForm.patchValue({
      id: manager.id,
      name: manager.name,
      surname: manager.surname,
      hiredDate: manager.hiredDate ? manager.hiredDate.format(DATE_TIME_FORMAT) : null,
      workers: manager.workers,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const manager = this.createFromForm();
    if (manager.id !== undefined) {
      this.subscribeToSaveResponse(this.managerService.update(manager));
    } else {
      this.subscribeToSaveResponse(this.managerService.create(manager));
    }
  }

  private createFromForm(): IManager {
    return {
      ...new Manager(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      surname: this.editForm.get(['surname'])!.value,
      hiredDate: this.editForm.get(['hiredDate'])!.value ? moment(this.editForm.get(['hiredDate'])!.value, DATE_TIME_FORMAT) : undefined,
      workers: this.editForm.get(['workers'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IManager>>): void {
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

  trackById(index: number, item: IWorker): any {
    return item.id;
  }

  getSelected(selectedVals: IWorker[], option: IWorker): IWorker {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
