import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IOrderItem } from 'app/shared/model/order-item.model';
import { JhiEventManager } from 'ng-jhipster';
import { Subscription } from 'rxjs';
import { OrderItemService } from './order-item.service';

@Component({
  selector: 'jhi-order-item-test',
  templateUrl: './order-item-test.component.html',
})
export class OrderItemTestComponent implements OnInit {
  orderItems?: IOrderItem[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected orderItemService: OrderItemService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  ngOnInit(): void {}
}
