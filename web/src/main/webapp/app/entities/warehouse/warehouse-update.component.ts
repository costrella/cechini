import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWarehouse, Warehouse } from 'app/shared/model/warehouse.model';
import { WarehouseService } from './warehouse.service';

@Component({
  selector: 'jhi-warehouse-update',
  templateUrl: './warehouse-update.component.html',
})
export class WarehouseUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    mail: [null, [Validators.pattern('^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$')]],
  });

  constructor(protected warehouseService: WarehouseService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ warehouse }) => {
      this.updateForm(warehouse);
    });
  }

  updateForm(warehouse: IWarehouse): void {
    this.editForm.patchValue({
      id: warehouse.id,
      name: warehouse.name,
      mail: warehouse.mail,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const warehouse = this.createFromForm();
    if (warehouse.id !== undefined) {
      this.subscribeToSaveResponse(this.warehouseService.update(warehouse));
    } else {
      this.subscribeToSaveResponse(this.warehouseService.create(warehouse));
    }
  }

  private createFromForm(): IWarehouse {
    return {
      ...new Warehouse(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      mail: this.editForm.get(['mail'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWarehouse>>): void {
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
