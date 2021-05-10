import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IStats, Stats } from 'app/shared/model/stats.model';
import { StatsService } from './stats.service';
import { StatsComponent } from './stats.component';
import { StatsDetailComponent } from './stats-detail.component';

@Injectable({ providedIn: 'root' })
export class StatsResolve implements Resolve<IStats> {
  constructor(private service: StatsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStats> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((stats: HttpResponse<Stats>) => {
          if (stats.body) {
            return of(stats.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Stats());
  }
}

export const statsRoute: Routes = [
  {
    path: '',
    component: StatsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cechiniApp.stats.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StatsDetailComponent,
    resolve: {
      stats: StatsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cechiniApp.stats.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
