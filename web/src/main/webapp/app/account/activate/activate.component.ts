import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { flatMap } from 'rxjs/operators';
import { JhiLanguageService } from 'ng-jhipster';
import { SessionStorageService } from 'ngx-webstorage';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { ActivateService } from './activate.service';

@Component({
  selector: 'jhi-activate',
  templateUrl: './activate.component.html',
})
export class ActivateComponent implements OnInit {
  error = false;
  success = false;

  constructor(
    private activateService: ActivateService,
    private loginModalService: LoginModalService,
    private route: ActivatedRoute,
    private languageService: JhiLanguageService,
    private sessionStorage: SessionStorageService
  ) {}

  ngOnInit(): void {
    this.route.queryParams
      .pipe(
        flatMap(params => {
          if (params.lang) {
            this.sessionStorage.store('locale', params.lang);
            this.languageService.changeLanguage(params.lang);
          }
          return this.activateService.get(params.key);
        })
      )
      .subscribe(
        () => (this.success = true),
        () => (this.error = true)
      );
  }

  login(): void {
    this.loginModalService.open();
  }
}
