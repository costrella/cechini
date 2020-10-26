import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IPhoto, Photo } from 'app/shared/model/photo.model';
import { PhotoService } from './photo.service';
import { IPhotoFile } from 'app/shared/model/photo-file.model';
import { PhotoFileService } from 'app/entities/photo-file/photo-file.service';
import { IReport } from 'app/shared/model/report.model';
import { ReportService } from 'app/entities/report/report.service';

type SelectableEntity = IPhotoFile | IReport;

@Component({
  selector: 'jhi-photo-update',
  templateUrl: './photo-update.component.html',
})
export class PhotoUpdateComponent implements OnInit {
  isSaving = false;
  files: IPhotoFile[] = [];
  reports: IReport[] = [];

  editForm = this.fb.group({
    id: [],
    uri: [],
    valueContentType: [],
    fileId: [],
    reportId: [],
  });

  constructor(
    protected photoService: PhotoService,
    protected photoFileService: PhotoFileService,
    protected reportService: ReportService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ photo }) => {
      this.updateForm(photo);

      this.photoFileService
        .query({ filter: 'photo-is-null' })
        .pipe(
          map((res: HttpResponse<IPhotoFile[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IPhotoFile[]) => {
          if (!photo.fileId) {
            this.files = resBody;
          } else {
            this.photoFileService
              .find(photo.fileId)
              .pipe(
                map((subRes: HttpResponse<IPhotoFile>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IPhotoFile[]) => (this.files = concatRes));
          }
        });

      this.reportService.query().subscribe((res: HttpResponse<IReport[]>) => (this.reports = res.body || []));
    });
  }

  updateForm(photo: IPhoto): void {
    this.editForm.patchValue({
      id: photo.id,
      uri: photo.uri,
      valueContentType: photo.valueContentType,
      fileId: photo.fileId,
      reportId: photo.reportId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const photo = this.createFromForm();
    if (photo.id !== undefined) {
      this.subscribeToSaveResponse(this.photoService.update(photo));
    } else {
      this.subscribeToSaveResponse(this.photoService.create(photo));
    }
  }

  private createFromForm(): IPhoto {
    return {
      ...new Photo(),
      id: this.editForm.get(['id'])!.value,
      uri: this.editForm.get(['uri'])!.value,
      valueContentType: this.editForm.get(['valueContentType'])!.value,
      fileId: this.editForm.get(['fileId'])!.value,
      reportId: this.editForm.get(['reportId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPhoto>>): void {
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
