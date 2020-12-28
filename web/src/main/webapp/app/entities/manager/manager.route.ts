import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IManager, Manager } from 'app/shared/model/manager.model';
import { ManagerService } from './manager.service';
import { ManagerComponent } from './manager.component';
import { ManagerDetailComponent } from './manager-detail.component';
import { ManagerUpdateComponent } from './manager-update.component';

@Injectable({ providedIn: 'root' })
export class ManagerResolve implements Resolve<IManager> {
  constructor(private service: ManagerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IManager> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((manager: HttpResponse<Manager>) => {
          if (manager.body) {
            return of(manager.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Manager());
  }
}

export const managerRoute: Routes = [
  {
    path: '',
    component: ManagerComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'cechiniApp.manager.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ManagerDetailComponent,
    resolve: {
      manager: ManagerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cechiniApp.manager.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ManagerUpdateComponent,
    resolve: {
      manager: ManagerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cechiniApp.manager.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ManagerUpdateComponent,
    resolve: {
      manager: ManagerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cechiniApp.manager.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
