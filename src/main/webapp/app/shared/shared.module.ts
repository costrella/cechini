import { NgModule } from '@angular/core';
import { OrderItemTestComponent } from 'app/entities/order-item/order-item-test.component';
import { OrderItemComponent } from 'app/entities/order-item/order-item.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { AlertComponent } from './alert/alert.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { LoginModalComponent } from './login/login.component';
import { CechiniSharedLibsModule } from './shared-libs.module';

@NgModule({
  imports: [CechiniSharedLibsModule],
  declarations: [
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    OrderItemTestComponent,
    OrderItemComponent,
  ],
  entryComponents: [LoginModalComponent],
  exports: [
    CechiniSharedLibsModule,
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    OrderItemTestComponent,
    OrderItemComponent,
  ],
})
export class CechiniSharedModule {}
