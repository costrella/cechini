import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { IStore } from 'app/shared/model/store.model';
import { IWorker, Worker } from 'app/shared/model/worker.model';
import { JhiEventManager } from 'ng-jhipster';
import { combineLatest, Subscription } from 'rxjs';
import { WorkerService } from '../worker/worker.service';
import { StoreDeleteDialogComponent } from './store-delete-dialog.component';
import { StoreService } from './store.service';

@Component({
  selector: 'jhi-store',
  templateUrl: './store.component.html',
})
export class StoreComponent implements OnInit, OnDestroy {
  stores?: IStore[];
  workers?: IWorker[];
  worker?: IWorker;
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  canvas: any;
  ctx: any;

  constructor(
    protected storeService: StoreService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected workerService: WorkerService
  ) {}

  filter(): void {
    const pageToLoad = 1;

    const workerId = this.worker?.id;

    if (workerId) {
      this.storeService
        .findAllByWorker(workerId, {
          page: 0,
          size: this.itemsPerPage,
          sort: this.sort(),
        })
        .subscribe(
          (res: HttpResponse<IStore[]>) => this.onSuccess(res.body, res.headers, pageToLoad, false),
          () => this.onError()
        );
    } else {
      this.loadPage();
    }
  }

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.storeService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IStore[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInStores();
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
  }

  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
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

  trackId(index: number, item: IStore): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInStores(): void {
    this.eventSubscriber = this.eventManager.subscribe('storeListModification', () => this.loadPage());
  }

  delete(store: IStore): void {
    const modalRef = this.modalService.open(StoreDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.store = store;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IStore[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/store'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.stores = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
