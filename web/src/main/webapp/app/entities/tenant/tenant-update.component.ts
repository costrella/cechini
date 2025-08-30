import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITenant, Tenant } from 'app/shared/model/tenant.model';
import { TenantService } from './tenant.service';
import { IWorker } from 'app/shared/model/worker.model';
import { WorkerService } from 'app/entities/worker/worker.service';
import { ILocation } from 'app/shared/model/location.model';
import { LocationService } from 'app/entities/location/location.service';

type SelectableEntity = IWorker | ILocation;

@Component({
  selector: 'jhi-tenant-update',
  templateUrl: './tenant-update.component.html',
})
export class TenantUpdateComponent implements OnInit {
  isSaving = false;
  workers: IWorker[] = [];
  locations: ILocation[] = [];

  editForm = this.fb.group({
    id: [],
    // name: [],
    // workerId: [],
    // locationId: [],
  });

  constructor(
    protected tenantService: TenantService,
    protected workerService: WorkerService,
    protected locationService: LocationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tenant }) => {
      this.updateForm(tenant);

      this.workerService.query().subscribe((res: HttpResponse<IWorker[]>) => (this.workers = res.body || []));

      this.locationService.query().subscribe((res: HttpResponse<ILocation[]>) => (this.locations = res.body || []));
    });
  }

  updateForm(tenant: ITenant): void {
    this.editForm.patchValue({
      id: tenant.id,
      // name: tenant.name,
      // workerId: tenant.workerId,
      // locationId: tenant.locationId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tenant = this.createFromForm();
    if (tenant.id !== undefined) {
      this.subscribeToSaveResponse(this.tenantService.update(tenant));
    } else {
      this.subscribeToSaveResponse(this.tenantService.create(tenant));
    }
  }

  private createFromForm(): ITenant {
    return {
      ...new Tenant(),
      id: this.editForm.get(['id'])!.value,
      // name: this.editForm.get(['name'])!.value,
      // workerId: this.editForm.get(['workerId'])!.value,
      // locationId: this.editForm.get(['locationId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITenant>>): void {
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

  tenantById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
