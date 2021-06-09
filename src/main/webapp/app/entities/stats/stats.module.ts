import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CechiniSharedModule } from 'app/shared/shared.module';
import { StatsComponent } from './stats.component';
import { StatsDetailComponent } from './stats-detail.component';
import { statsRoute } from './stats.route';

@NgModule({
  imports: [CechiniSharedModule, RouterModule.forChild(statsRoute)],
  declarations: [StatsComponent, StatsDetailComponent],
})
export class CechiniStatsModule {}
