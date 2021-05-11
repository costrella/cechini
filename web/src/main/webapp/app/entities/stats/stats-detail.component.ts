import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStats } from 'app/shared/model/stats.model';

@Component({
  selector: 'jhi-stats-detail',
  templateUrl: './stats-detail.component.html',
})
export class StatsDetailComponent implements OnInit {
  stats: IStats | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stats }) => (this.stats = stats));
  }

  previousState(): void {
    window.history.back();
  }
}
