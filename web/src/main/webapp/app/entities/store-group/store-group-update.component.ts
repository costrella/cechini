import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IStoreGroup, StoreGroup } from 'app/shared/model/store-group.model';
import { StoreGroupService } from './store-group.service';

@Component({
  selector: 'jhi-store-group-update',
  templateUrl: './store-group-update.component.html',
})
export class StoreGroupUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
  });

  constructor(protected storeGroupService: StoreGroupService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ storeGroup }) => {
      this.updateForm(storeGroup);
    });
  }

  updateForm(storeGroup: IStoreGroup): void {
    this.editForm.patchValue({
      id: storeGroup.id,
      name: storeGroup.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const storeGroup = this.createFromForm();
    if (storeGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.storeGroupService.update(storeGroup));
    } else {
      this.subscribeToSaveResponse(this.storeGroupService.create(storeGroup));
    }
  }

  private createFromForm(): IStoreGroup {
    return {
      ...new StoreGroup(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStoreGroup>>): void {
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
