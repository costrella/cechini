import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'tenant',
        loadChildren: () => import('./tenant/tenant.module').then(m => m.CechiniTenantModule),
      },
      {
        path: 'worker',
        loadChildren: () => import('./worker/worker.module').then(m => m.CechiniWorkerModule),
      },
      {
        path: 'store',
        loadChildren: () => import('./store/store.module').then(m => m.CechiniStoreModule),
      },
      {
        path: 'store-group',
        loadChildren: () => import('./store-group/store-group.module').then(m => m.CechiniStoreGroupModule),
      },
      {
        path: 'manager',
        loadChildren: () => import('./manager/manager.module').then(m => m.CechiniManagerModule),
      },
      {
        path: 'order',
        loadChildren: () => import('./order/order.module').then(m => m.CechiniOrderModule),
      },
      {
        path: 'order-item',
        loadChildren: () => import('./order-item/order-item.module').then(m => m.CechiniOrderItemModule),
      },
      {
        path: 'product',
        loadChildren: () => import('./product/product.module').then(m => m.CechiniProductModule),
      },
      {
        path: 'warehouse',
        loadChildren: () => import('./warehouse/warehouse.module').then(m => m.CechiniWarehouseModule),
      },
      {
        path: 'report',
        loadChildren: () => import('./report/report.module').then(m => m.CechiniReportModule),
      },
      {
        path: 'track',
        loadChildren: () => import('./track/track.module').then(m => m.CechiniTrackModule),
      },
      {
        path: 'location',
        loadChildren: () => import('./location/location.module').then(m => m.CechiniLocationModule),
      },
      {
        path: 'status',
        loadChildren: () => import('./status/status.module').then(m => m.CechiniStatusModule),
      },
      {
        path: 'photo',
        loadChildren: () => import('./photo/photo.module').then(m => m.CechiniPhotoModule),
      },
      {
        path: 'note',
        loadChildren: () => import('./note/note.module').then(m => m.CechiniNoteModule),
      },
      {
        path: 'photo-file',
        loadChildren: () => import('./photo-file/photo-file.module').then(m => m.CechiniPhotoFileModule),
      },
      {
        path: 'stats',
        loadChildren: () => import('./stats/stats.module').then(m => m.CechiniStatsModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class CechiniEntityModule {}
