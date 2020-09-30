import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CechiniSharedModule } from 'app/shared/shared.module';
import { ReportDeleteDialogComponent } from './report-delete-dialog.component';
import { ReportDetailComponent } from './report-detail.component';
import { ReportUpdateComponent } from './report-update.component';
import { reportRoute } from './report.route';

@NgModule({
  imports: [CechiniSharedModule, RouterModule.forChild(reportRoute)],
  declarations: [ReportDetailComponent, ReportUpdateComponent, ReportDeleteDialogComponent],
  entryComponents: [ReportDeleteDialogComponent],
})
export class CechiniReportModule {}
