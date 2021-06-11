import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IReport } from 'app/shared/model/report.model';
import { IStore } from 'app/shared/model/store.model';
import { ReportService } from '../report/report.service';

@Component({
  selector: 'jhi-store-detail',
  templateUrl: './store-detail.component.html',
})
export class StoreDetailComponent implements OnInit {
  store: IStore | null = null;
  reports: IReport[] | null = null;
  active: any = 1;

  constructor(protected activatedRoute: ActivatedRoute, protected reportService: ReportService) {}
  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ store }) => (this.store = store));

    this.reportService.findAllByStoreIdAndMonth(this.store?.id || 0, 2).subscribe(
      (res: HttpResponse<IReport[]>) => {
        this.reports = res.body;
        // if (this.reports)
        //   this.active = this.reports[0].reportDate;
        console.log('CCCC reports: ' + this.reports?.length);
      },
      () => ''
    );
  }

  previousState(): void {
    window.history.back();
  }
}
