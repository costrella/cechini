import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRoute, Route } from 'app/shared/model/route.model';
import { RouteService } from './route.service';
import { IWorker } from 'app/shared/model/worker.model';
import { WorkerService } from 'app/entities/worker/worker.service';
import { ILocation } from 'app/shared/model/location.model';
import { LocationService } from 'app/entities/location/location.service';

type SelectableEntity = IWorker | ILocation;

@Component({
  selector: 'jhi-route-update',
  templateUrl: './route-update.component.html',
})
export class RouteUpdateComponent implements OnInit {
  isSaving = false;
  workers: IWorker[] = [];
  locations: ILocation[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    workerId: [],
    locationId: [],
  });

  constructor(
    protected routeService: RouteService,
    protected workerService: WorkerService,
    protected locationService: LocationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ route }) => {
      this.updateForm(route);

      this.workerService.query().subscribe((res: HttpResponse<IWorker[]>) => (this.workers = res.body || []));

      this.locationService.query().subscribe((res: HttpResponse<ILocation[]>) => (this.locations = res.body || []));
    });
  }

  updateForm(route: IRoute): void {
    this.editForm.patchValue({
      id: route.id,
      name: route.name,
      workerId: route.workerId,
      locationId: route.locationId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const route = this.createFromForm();
    if (route.id !== undefined) {
      this.subscribeToSaveResponse(this.routeService.update(route));
    } else {
      this.subscribeToSaveResponse(this.routeService.create(route));
    }
  }

  private createFromForm(): IRoute {
    return {
      ...new Route(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      workerId: this.editForm.get(['workerId'])!.value,
      locationId: this.editForm.get(['locationId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRoute>>): void {
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
