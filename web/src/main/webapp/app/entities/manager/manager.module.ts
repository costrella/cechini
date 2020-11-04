import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CechiniSharedModule } from 'app/shared/shared.module';
import { ManagerComponent } from './manager.component';
import { ManagerDetailComponent } from './manager-detail.component';
import { ManagerUpdateComponent } from './manager-update.component';
import { ManagerDeleteDialogComponent } from './manager-delete-dialog.component';
import { managerRoute } from './manager.route';

@NgModule({
  imports: [CechiniSharedModule, RouterModule.forChild(managerRoute)],
  declarations: [ManagerComponent, ManagerDetailComponent, ManagerUpdateComponent, ManagerDeleteDialogComponent],
  entryComponents: [ManagerDeleteDialogComponent],
})
export class CechiniManagerModule {}
