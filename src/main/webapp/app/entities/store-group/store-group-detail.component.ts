import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStoreGroup } from 'app/shared/model/store-group.model';

@Component({
  selector: 'jhi-store-group-detail',
  templateUrl: './store-group-detail.component.html',
})
export class StoreGroupDetailComponent implements OnInit {
  storeGroup: IStoreGroup | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ storeGroup }) => (this.storeGroup = storeGroup));
  }

  previousState(): void {
    window.history.back();
  }
}
