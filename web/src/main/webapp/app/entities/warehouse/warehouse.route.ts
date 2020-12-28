import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWarehouse, Warehouse } from 'app/shared/model/warehouse.model';
import { WarehouseService } from './warehouse.service';
import { WarehouseComponent } from './warehouse.component';
import { WarehouseDetailComponent } from './warehouse-detail.component';
import { WarehouseUpdateComponent } from './warehouse-update.component';

@Injectable({ providedIn: 'root' })
export class WarehouseResolve implements Resolve<IWarehouse> {
  constructor(private service: WarehouseService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWarehouse> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((warehouse: HttpResponse<Warehouse>) => {
          if (warehouse.body) {
            return of(warehouse.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Warehouse());
  }
}

export const warehouseRoute: Routes = [
  {
    path: '',
    component: WarehouseComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'cechiniApp.warehouse.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WarehouseDetailComponent,
    resolve: {
      warehouse: WarehouseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cechiniApp.warehouse.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WarehouseUpdateComponent,
    resolve: {
      warehouse: WarehouseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cechiniApp.warehouse.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WarehouseUpdateComponent,
    resolve: {
      warehouse: WarehouseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cechiniApp.warehouse.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
