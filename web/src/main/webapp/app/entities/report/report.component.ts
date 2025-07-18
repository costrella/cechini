import { DatePipe } from '@angular/common';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { IReport } from 'app/shared/model/report.model';
import { IStore } from 'app/shared/model/store.model';
import { IWorker } from 'app/shared/model/worker.model';
import { JhiEventManager } from 'ng-jhipster';
import { CookieService } from 'ngx-cookie-service';
import { combineLatest, Subscription } from 'rxjs';
import { StoreService } from '../store/store.service';
import { WorkerService } from '../worker/worker.service';
import { ReportDeleteDialogComponent } from './report-delete-dialog.component';
import { ReportService } from './report.service';

export enum ReportPageType {
  WORKER = 'WORKER',
  STORE = 'STORE',
}

@Component({
  selector: 'jhi-report',
  templateUrl: './report.component.html',
})
export class ReportComponent implements OnInit, OnDestroy {
  reports?: IReport[];
  stores?: IStore[];
  workers?: IWorker[];
  worker?: IWorker;
  store?: IStore;
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  internal?: boolean;
  type?: ReportPageType;
  storeId?: number;
  workerId?: number;
  fromDate = '';
  toDate = '';
  previousMonthDate = '';
  todayDate = '';
  reportIdRow?: number;
  private dateFormat = 'yyyy-MM-dd';

  constructor(
    protected reportService: ReportService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected workerService: WorkerService,
    protected storeService: StoreService,
    private datePipe: DatePipe,
    private cookieService: CookieService
  ) {}

  @Input()
  set setStoreId(storeId: number) {
    this.internal = true;
    this.type = ReportPageType.STORE;
    this.storeId = storeId;
  }

  @Input()
  set setWorkerId(workerId: number) {
    this.internal = true;
    this.type = ReportPageType.WORKER;
    this.workerId = workerId;
  }

  clickItem(array: any[]): void {
    this.cookieService.set('reportId_row', '' + array[3]);
    this.router.navigate([array[0] + array[1] + array[2]]);
  }

  filter(): void {
    const workerId = this.worker?.id || 0;
    this.cookieService.set('workerId', '' + workerId);
    this.cookieService.set('fromDate', '' + this.fromDate);
    this.cookieService.set('toDate', '' + this.toDate);

    this.handleNavigation(true);
  }

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    if (this.internal) {
      switch (this.type) {
        case ReportPageType.STORE:
          this.reportService
            .findAllByStoreId(this.storeId || 0, {
              page: pageToLoad - 1,
              size: this.itemsPerPage,
              sort: this.sort(),
            })
            .subscribe(
              (res: HttpResponse<IReport[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
              () => this.onError()
            );
          break;

        case ReportPageType.WORKER:
          this.reportService
            .findAllByWorkerId(this.workerId || 0, {
              page: pageToLoad - 1,
              size: this.itemsPerPage,
              sort: this.sort(),
            })
            .subscribe(
              (res: HttpResponse<IReport[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
              () => this.onError()
            );
          break;
      }
    } else {
      const workerId = this.worker?.id || 0;
      const storeId = this.store?.id || 0;

      this.reportService
        .findByStoreAndWorker(storeId, workerId, {
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          fromDate: this.fromDate,
          toDate: this.toDate,
        })
        .subscribe(
          (res: HttpResponse<IReport[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    }
  }

  ngOnInit(): void {
    const reportIdRowString = this.cookieService.get('reportId_row');
    if (reportIdRowString != null) {
      this.reportIdRow = Number(reportIdRowString);
    }

    this.initDate();

    // this.handleNavigation();
    this.registerChangeInReports();
    this.initFilter();
  }

  initDate(): void {
    this.previousMonthDate = this.previousMonth();
    const fromDateCookie = this.cookieService.get('fromDate');
    if (fromDateCookie !== '') {
      this.fromDate = fromDateCookie;
    } else {
      this.fromDate = this.previousMonthDate;
    }

    this.todayDate = this.today();
    const toDateCookie = this.cookieService.get('toDate');
    if (toDateCookie !== '') {
      this.toDate = toDateCookie;
    } else {
      this.toDate = this.todayDate;
    }
  }

  clear(): void {
    this.toDate = this.todayDate;
    this.fromDate = this.previousMonthDate;
    this.worker = undefined;

    this.cookieService.set('workerId', '');
    this.cookieService.set('fromDate', '');
    this.cookieService.set('toDate', '');

    this.filter();
  }

  initFilter(): void {
    const workerId: string = this.cookieService.get('workerId');
    this.workerService.findAll().subscribe(
      (res: HttpResponse<IWorker[]>) => {
        this.workers = res.body || [];
        this.worker = this.workers.find(x => x.id === Number(workerId));
        if (this.worker != null) {
          this.filter();
        } else {
          this.handleNavigation();
        }
      },
      () => this.onError()
    );
  }

  protected handleNavigation(isFilter?: boolean): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = this.internal ? ['id', 'asc'] : (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === 'asc';
      if (isFilter || pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;

        this.loadPage(pageNumber, true);
      }
    }).subscribe();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IReport): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInReports(): void {
    this.eventSubscriber = this.eventManager.subscribe('reportListModification', () => this.loadPage());
  }

  delete(report: IReport): void {
    const modalRef = this.modalService.open(ReportDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.report = report;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IReport[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/report'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.reports = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }

  private previousMonth(): string {
    let date = new Date();
    if (date.getMonth() === 0) {
      date = new Date(date.getFullYear() - 1, 11, date.getDate());
    } else {
      date = new Date(date.getFullYear(), date.getMonth() - 1, date.getDate());
    }
    return this.datePipe.transform(date, this.dateFormat)!;
  }

  private today(): string {
    // Today + 1 day - needed if the current day must be included
    const date = new Date();
    date.setDate(date.getDate() + 1);
    return this.datePipe.transform(date, this.dateFormat)!;
  }
}
