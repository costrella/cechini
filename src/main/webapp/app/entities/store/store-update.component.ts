import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IStore, Store } from 'app/shared/model/store.model';
import { StoreService } from './store.service';
import { IWorker } from 'app/shared/model/worker.model';
import { WorkerService } from 'app/entities/worker/worker.service';
import { ILocation } from 'app/shared/model/location.model';
import { LocationService } from 'app/entities/location/location.service';
import { IStoreGroup } from 'app/shared/model/store-group.model';
import { StoreGroupService } from 'app/entities/store-group/store-group.service';

type SelectableEntity = IWorker | ILocation | IStoreGroup;

@Component({
  selector: 'jhi-store-update',
  templateUrl: './store-update.component.html',
})
export class StoreUpdateComponent implements OnInit {
  isSaving = false;
  workers: IWorker[] = [];
  locations: ILocation[] = [];
  storegroups: IStoreGroup[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    workerId: [],
    locationId: [],
    storegroupId: [],
  });

  constructor(
    protected storeService: StoreService,
    protected workerService: WorkerService,
    protected locationService: LocationService,
    protected storeGroupService: StoreGroupService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ store }) => {
      this.updateForm(store);

      this.workerService.query().subscribe((res: HttpResponse<IWorker[]>) => (this.workers = res.body || []));

      this.locationService.query().subscribe((res: HttpResponse<ILocation[]>) => (this.locations = res.body || []));

      this.storeGroupService.query().subscribe((res: HttpResponse<IStoreGroup[]>) => (this.storegroups = res.body || []));
    });
  }

  updateForm(store: IStore): void {
    this.editForm.patchValue({
      id: store.id,
      name: store.name,
      workerId: store.workerId,
      locationId: store.locationId,
      storegroupId: store.storegroupId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const store = this.createFromForm();
    if (store.id !== undefined) {
      this.subscribeToSaveResponse(this.storeService.update(store));
    } else {
      this.subscribeToSaveResponse(this.storeService.create(store));
    }
  }

  private createFromForm(): IStore {
    return {
      ...new Store(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      workerId: this.editForm.get(['workerId'])!.value,
      locationId: this.editForm.get(['locationId'])!.value,
      storegroupId: this.editForm.get(['storegroupId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStore>>): void {
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
