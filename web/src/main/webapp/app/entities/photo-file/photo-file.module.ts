import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CechiniSharedModule } from 'app/shared/shared.module';
import { PhotoFileComponent } from './photo-file.component';
import { PhotoFileDetailComponent } from './photo-file-detail.component';
import { PhotoFileUpdateComponent } from './photo-file-update.component';
import { PhotoFileDeleteDialogComponent } from './photo-file-delete-dialog.component';
import { photoFileRoute } from './photo-file.route';

@NgModule({
  imports: [CechiniSharedModule, RouterModule.forChild(photoFileRoute)],
  declarations: [PhotoFileComponent, PhotoFileDetailComponent, PhotoFileUpdateComponent, PhotoFileDeleteDialogComponent],
  entryComponents: [PhotoFileDeleteDialogComponent],
})
export class CechiniPhotoFileModule {}
