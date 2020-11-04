import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IStoreGroup, StoreGroup } from 'app/shared/model/store-group.model';
import { StoreGroupService } from './store-group.service';
import { StoreGroupComponent } from './store-group.component';
import { StoreGroupDetailComponent } from './store-group-detail.component';
import { StoreGroupUpdateComponent } from './store-group-update.component';

@Injectable({ providedIn: 'root' })
export class StoreGroupResolve implements Resolve<IStoreGroup> {
  constructor(private service: StoreGroupService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStoreGroup> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((storeGroup: HttpResponse<StoreGroup>) => {
          if (storeGroup.body) {
            return of(storeGroup.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new StoreGroup());
  }
}

export const storeGroupRoute: Routes = [
  {
    path: '',
    component: StoreGroupComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'cechiniApp.storeGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StoreGroupDetailComponent,
    resolve: {
      storeGroup: StoreGroupResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cechiniApp.storeGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StoreGroupUpdateComponent,
    resolve: {
      storeGroup: StoreGroupResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cechiniApp.storeGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StoreGroupUpdateComponent,
    resolve: {
      storeGroup: StoreGroupResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cechiniApp.storeGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
