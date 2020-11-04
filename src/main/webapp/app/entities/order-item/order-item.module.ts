import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CechiniSharedModule } from 'app/shared/shared.module';
import { OrderItemDeleteDialogComponent } from './order-item-delete-dialog.component';
import { OrderItemDetailComponent } from './order-item-detail.component';
import { OrderItemUpdateComponent } from './order-item-update.component';
import { orderItemRoute } from './order-item.route';

@NgModule({
  imports: [CechiniSharedModule, RouterModule.forChild(orderItemRoute)],
  declarations: [OrderItemDetailComponent, OrderItemUpdateComponent, OrderItemDeleteDialogComponent],
  entryComponents: [OrderItemDeleteDialogComponent],
})
export class CechiniOrderItemModule {}
