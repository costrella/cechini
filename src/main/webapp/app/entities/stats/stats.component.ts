import { HttpResponse } from '@angular/common/http';
import { AfterViewInit, Component, OnDestroy, OnInit } from '@angular/core';
import { IChart01 } from 'app/shared/model/chart01.model';
import { IStats } from 'app/shared/model/stats.model';
import { JhiEventManager } from 'ng-jhipster';
import { Subscription } from 'rxjs';
import { StatsService } from './stats.service';
import * as Chart from 'chart.js';

@Component({
  selector: 'jhi-stats',
  templateUrl: './stats.component.html',
})
export class StatsComponent implements OnInit, OnDestroy, AfterViewInit {
  stats?: IStats[];
  eventSubscriber?: Subscription;
  canvas: any;
  canvas02: any;
  canvas03: any;
  ctx: any;
  ctx02: any;
  ctx03: any;

  constructor(protected statsService: StatsService, protected eventManager: JhiEventManager) {}

  chart01(): void {
    this.canvas = document.getElementById('myChart');
    this.ctx = this.canvas.getContext('2d');
    // eslint-disable-next-line no-unused-vars

    this.statsService.chart01().subscribe(
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
              text: 'Wykres ilości przesłanych raportów',
            },
          },
        });
      },
      () => this.onError()
    );
  }

  chart02(): void {
    this.canvas02 = document.getElementById('myChart02');
    this.ctx02 = this.canvas02.getContext('2d');
    // eslint-disable-next-line no-unused-vars

    this.statsService.chart02().subscribe(
      (res: HttpResponse<IChart01>) => {
        const months = res.body?.monthsName;

        const myChart = new Chart(this.ctx02, {
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
              text: 'Wykres ilości przesłanych zamówień',
            },
          },
        });
      },
      () => this.onError()
    );
  }

  chart03(): void {
    this.canvas03 = document.getElementById('myChart03');
    this.ctx03 = this.canvas03.getContext('2d');
    // eslint-disable-next-line no-unused-vars

    this.statsService.chart03().subscribe(
      (res: HttpResponse<IChart01>) => {
        const months = res.body?.monthsName;

        const myChart = new Chart(this.ctx03, {
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
              text: 'Wykres ilości sprzedaży zgrzewek produktów',
            },
          },
        });
      },
      () => this.onError()
    );
  }

  ngAfterViewInit(): void {
    this.chart01();
    this.chart02();
    this.chart03();
  }

  loadAll(): void {
    this.statsService.query().subscribe((res: HttpResponse<IStats[]>) => (this.stats = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInStats();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IStats): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInStats(): void {
    this.eventSubscriber = this.eventManager.subscribe('statsListModification', () => this.loadAll());
  }

  protected onError(): void {}
}
