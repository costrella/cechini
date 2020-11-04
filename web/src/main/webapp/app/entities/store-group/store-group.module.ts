import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CechiniSharedModule } from 'app/shared/shared.module';
import { StoreGroupComponent } from './store-group.component';
import { StoreGroupDetailComponent } from './store-group-detail.component';
import { StoreGroupUpdateComponent } from './store-group-update.component';
import { StoreGroupDeleteDialogComponent } from './store-group-delete-dialog.component';
import { storeGroupRoute } from './store-group.route';

@NgModule({
  imports: [CechiniSharedModule, RouterModule.forChild(storeGroupRoute)],
  declarations: [StoreGroupComponent, StoreGroupDetailComponent, StoreGroupUpdateComponent, StoreGroupDeleteDialogComponent],
  entryComponents: [StoreGroupDeleteDialogComponent],
})
export class CechiniStoreGroupModule {}
