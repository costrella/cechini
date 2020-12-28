import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { INote, Note } from 'app/shared/model/note.model';
import { NoteService } from './note.service';
import { IStore } from 'app/shared/model/store.model';
import { StoreService } from 'app/entities/store/store.service';
import { IWorker } from 'app/shared/model/worker.model';
import { WorkerService } from 'app/entities/worker/worker.service';
import { IManager } from 'app/shared/model/manager.model';
import { ManagerService } from 'app/entities/manager/manager.service';
import { IReport } from 'app/shared/model/report.model';
import { ReportService } from 'app/entities/report/report.service';

type SelectableEntity = IStore | IWorker | IManager | IReport;

@Component({
  selector: 'jhi-note-update',
  templateUrl: './note-update.component.html',
})
export class NoteUpdateComponent implements OnInit {
  isSaving = false;
  stores: IStore[] = [];
  workers: IWorker[] = [];
  managers: IManager[] = [];
  reports: IReport[] = [];

  editForm = this.fb.group({
    id: [],
    value: [null, [Validators.maxLength(2000)]],
    date: [],
    storeId: [],
    workerNoteId: [],
    managerNoteId: [],
    reportId: [],
  });

  constructor(
    protected noteService: NoteService,
    protected storeService: StoreService,
    protected workerService: WorkerService,
    protected managerService: ManagerService,
    protected reportService: ReportService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ note }) => {
      if (!note.id) {
        const today = moment().startOf('day');
        note.date = today;
      }

      this.updateForm(note);

      this.storeService.query().subscribe((res: HttpResponse<IStore[]>) => (this.stores = res.body || []));

      this.workerService.query().subscribe((res: HttpResponse<IWorker[]>) => (this.workers = res.body || []));

      this.managerService.query().subscribe((res: HttpResponse<IManager[]>) => (this.managers = res.body || []));

      this.reportService.query().subscribe((res: HttpResponse<IReport[]>) => (this.reports = res.body || []));
    });
  }

  updateForm(note: INote): void {
    this.editForm.patchValue({
      id: note.id,
      value: note.value,
      date: note.date ? note.date.format(DATE_TIME_FORMAT) : null,
      storeId: note.storeId,
      workerNoteId: note.workerNoteId,
      managerNoteId: note.managerNoteId,
      reportId: note.reportId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const note = this.createFromForm();
    if (note.id !== undefined) {
      this.subscribeToSaveResponse(this.noteService.update(note));
    } else {
      this.subscribeToSaveResponse(this.noteService.create(note));
    }
  }

  private createFromForm(): INote {
    return {
      ...new Note(),
      id: this.editForm.get(['id'])!.value,
      value: this.editForm.get(['value'])!.value,
      date: this.editForm.get(['date'])!.value ? moment(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      storeId: this.editForm.get(['storeId'])!.value,
      workerNoteId: this.editForm.get(['workerNoteId'])!.value,
      managerNoteId: this.editForm.get(['managerNoteId'])!.value,
      reportId: this.editForm.get(['reportId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INote>>): void {
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
