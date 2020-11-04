import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPhotoFile, PhotoFile } from 'app/shared/model/photo-file.model';
import { PhotoFileService } from './photo-file.service';
import { PhotoFileComponent } from './photo-file.component';
import { PhotoFileDetailComponent } from './photo-file-detail.component';
import { PhotoFileUpdateComponent } from './photo-file-update.component';

@Injectable({ providedIn: 'root' })
export class PhotoFileResolve implements Resolve<IPhotoFile> {
  constructor(private service: PhotoFileService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPhotoFile> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((photoFile: HttpResponse<PhotoFile>) => {
          if (photoFile.body) {
            return of(photoFile.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PhotoFile());
  }
}

export const photoFileRoute: Routes = [
  {
    path: '',
    component: PhotoFileComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cechiniApp.photoFile.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PhotoFileDetailComponent,
    resolve: {
      photoFile: PhotoFileResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cechiniApp.photoFile.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PhotoFileUpdateComponent,
    resolve: {
      photoFile: PhotoFileResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cechiniApp.photoFile.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PhotoFileUpdateComponent,
    resolve: {
      photoFile: PhotoFileResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cechiniApp.photoFile.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
