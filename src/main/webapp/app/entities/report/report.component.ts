import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { IReport } from 'app/shared/model/report.model';
import { IStore, Store } from 'app/shared/model/store.model';
import { IWorker, Worker } from 'app/shared/model/worker.model';
import { JhiEventManager } from 'ng-jhipster';
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

  constructor(
    protected reportService: ReportService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected workerService: WorkerService,
    protected storeService: StoreService
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
    this.router.navigate([array[0] + array[1] + array[2]]);
  }

  filter(): void {
    const pageToLoad = 1;

    const workerId = this.worker?.id || 0;
    const storeId = this.store?.id || 0;

    this.reportService
      .findByStoreAndWorker(storeId, workerId, {
        page: 0,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IReport[]>) => this.onSuccess(res.body, res.headers, pageToLoad, false),
        () => this.onError()
      );
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
      this.reportService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
        })
        .subscribe(
          (res: HttpResponse<IReport[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    }
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInReports();
    this.initFilter();
  }

  initFilter(): void {
    this.worker = new Worker();
    this.workerService.findAll().subscribe(
      (res: HttpResponse<IWorker[]>) => {
        this.workers = res.body || [];
      },
      () => this.onError()
    );

    this.store = new Store();
    this.storeService.findAll().subscribe(
      (res: HttpResponse<IStore[]>) => {
        this.stores = res.body || [];
      },
      () => this.onError()
    );
  }

  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = this.internal ? ['id', 'asc'] : (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === 'asc';
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
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
}