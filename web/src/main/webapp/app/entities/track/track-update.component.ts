import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITrack, Track } from 'app/shared/model/track.model';
import { TrackService } from './track.service';
import { IWorker } from 'app/shared/model/worker.model';
import { WorkerService } from 'app/entities/worker/worker.service';
import { ILocation } from 'app/shared/model/location.model';
import { LocationService } from 'app/entities/location/location.service';

type SelectableEntity = IWorker | ILocation;

@Component({
  selector: 'jhi-track-update',
  templateUrl: './track-update.component.html',
})
export class TrackUpdateComponent implements OnInit {
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
    protected trackService: TrackService,
    protected workerService: WorkerService,
    protected locationService: LocationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ track }) => {
      this.updateForm(track);

      this.workerService.query().subscribe((res: HttpResponse<IWorker[]>) => (this.workers = res.body || []));

      this.locationService.query().subscribe((res: HttpResponse<ILocation[]>) => (this.locations = res.body || []));
    });
  }

  updateForm(track: ITrack): void {
    this.editForm.patchValue({
      id: track.id,
      name: track.name,
      workerId: track.workerId,
      locationId: track.locationId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const track = this.createFromForm();
    if (track.id !== undefined) {
      this.subscribeToSaveResponse(this.trackService.update(track));
    } else {
      this.subscribeToSaveResponse(this.trackService.create(track));
    }
  }

  private createFromForm(): ITrack {
    return {
      ...new Track(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      workerId: this.editForm.get(['workerId'])!.value,
      locationId: this.editForm.get(['locationId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrack>>): void {
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
