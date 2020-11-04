import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPhotoFile, PhotoFile } from 'app/shared/model/photo-file.model';
import { PhotoFileService } from './photo-file.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-photo-file-update',
  templateUrl: './photo-file-update.component.html',
})
export class PhotoFileUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    value: [],
    valueContentType: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected photoFileService: PhotoFileService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ photoFile }) => {
      this.updateForm(photoFile);
    });
  }

  updateForm(photoFile: IPhotoFile): void {
    this.editForm.patchValue({
      id: photoFile.id,
      value: photoFile.value,
      valueContentType: photoFile.valueContentType,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('cechiniApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const photoFile = this.createFromForm();
    if (photoFile.id !== undefined) {
      this.subscribeToSaveResponse(this.photoFileService.update(photoFile));
    } else {
      this.subscribeToSaveResponse(this.photoFileService.create(photoFile));
    }
  }

  private createFromForm(): IPhotoFile {
    return {
      ...new PhotoFile(),
      id: this.editForm.get(['id'])!.value,
      valueContentType: this.editForm.get(['valueContentType'])!.value,
      value: this.editForm.get(['value'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPhotoFile>>): void {
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
