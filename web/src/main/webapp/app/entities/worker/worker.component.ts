import { Component, OnInit, OnDestroy, AfterViewInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWorker } from 'app/shared/model/worker.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { WorkerService } from './worker.service';
import { WorkerDeleteDialogComponent } from './worker-delete-dialog.component';
import { IChart01 } from 'app/shared/model/chart01.model';
import * as Chart from 'chart.js';

@Component({
  selector: 'jhi-worker',
  templateUrl: './worker.component.html',
})
export class WorkerComponent implements OnInit, OnDestroy, AfterViewInit {
  workers?: IWorker[];
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
    protected workerService: WorkerService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  ngAfterViewInit(): void {
    this.canvas = document.getElementById('myChart');
    this.ctx = this.canvas.getContext('2d');
    // eslint-disable-next-line no-unused-vars

    this.workerService.chart01().subscribe(
      (res: HttpResponse<IChart01>) => {
        const months = res.body?.monthsName;

        const myChart = new Chart(this.ctx, {
          type: 'line',
          data: {
            labels: months,
            datasets: res.body?.details,
          },
          options: {
            scales: {
              yAxes: [
                {
                  ticks: {
                    beginAtZero: true,
                  },
                },
              ],
            },
            legend: {
              display: true,
            },
            title: {
              display: true,
              text: 'Wykres 01',
            },
          },
        });
      },
      () => this.onError()
    );
  }

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.workerService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IWorker[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInWorkers();
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

  trackId(index: number, item: IWorker): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInWorkers(): void {
    this.eventSubscriber = this.eventManager.subscribe('workerListModification', () => this.loadPage());
  }

  delete(worker: IWorker): void {
    const modalRef = this.modalService.open(WorkerDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.worker = worker;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IWorker[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/worker'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.workers = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
