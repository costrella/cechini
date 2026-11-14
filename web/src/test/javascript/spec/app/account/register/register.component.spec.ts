import { ComponentFixture, TestBed, async, inject, tick, fakeAsync } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { of, throwError } from 'rxjs';
import { JhiLanguageService } from 'ng-jhipster';

import { MockLanguageService } from '../../../helpers/mock-language.service';
import { CechiniTestModule } from '../../../test.module';
import { EMAIL_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE } from 'app/shared/constants/error.constants';
import { RegisterService } from 'app/account/register/register.service';
import { RegisterComponent } from 'app/account/register/register.component';

describe('Component Tests', () => {
  describe('RegisterComponent', () => {
    let fixture: ComponentFixture<RegisterComponent>;
    let comp: RegisterComponent;

    beforeEach(async(() => {
      TestBed.configureTestingModule({
        imports: [CechiniTestModule],
        declarations: [RegisterComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RegisterComponent, '')
        .compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(RegisterComponent);
      comp = fixture.componentInstance;
    });

    it('should ensure the two passwords entered match', () => {
      comp.registerForm.patchValue({
        password: 'password',
        confirmPassword: 'non-matching',
      });

      comp.register();

      expect(comp.doNotMatch).toBe(true);
    });

    it('should not submit when the email addresses do not match', inject([RegisterService], (service: RegisterService) => {
      spyOn(service, 'save').and.returnValue(of({}));
      comp.registerForm.patchValue({
        email: 'first@example.com',
        confirmEmail: 'second@example.com',
        password: 'password',
        confirmPassword: 'password',
      });

      comp.register();

      expect(comp.emailDoNotMatch).toBe(true);
      expect(service.save).not.toHaveBeenCalled();
    }));

    it('should update success to true after creating an account', inject(
      [RegisterService, JhiLanguageService],
      fakeAsync((service: RegisterService, mockTranslate: MockLanguageService) => {
        spyOn(service, 'save').and.returnValue(of({}));
        comp.registerForm.patchValue({
          password: 'password',
          confirmPassword: 'password',
        });

        comp.register();
        tick();

        expect(service.save).toHaveBeenCalledWith({
          email: '',
          password: 'password',
          login: '',
          website: '',
          langKey: 'en',
        });
        expect(comp.success).toBe(true);
        expect(mockTranslate.getCurrentLanguageSpy).toHaveBeenCalled();
        expect(comp.errorUserExists).toBe(false);
        expect(comp.errorEmailExists).toBe(false);
        expect(comp.error).toBe(false);
      })
    ));

    it('should notify of user existence upon 400/login already in use', inject(
      [RegisterService],
      fakeAsync((service: RegisterService) => {
        spyOn(service, 'save').and.returnValue(
          throwError({
            status: 400,
            error: { type: LOGIN_ALREADY_USED_TYPE },
          })
        );
        comp.registerForm.patchValue({
          password: 'password',
          confirmPassword: 'password',
        });

        comp.register();
        tick();

        expect(comp.errorUserExists).toBe(true);
        expect(comp.errorEmailExists).toBe(false);
        expect(comp.error).toBe(false);
      })
    ));

    it('should notify of email existence upon 400/email address already in use', inject(
      [RegisterService],
      fakeAsync((service: RegisterService) => {
        spyOn(service, 'save').and.returnValue(
          throwError({
            status: 400,
            error: { type: EMAIL_ALREADY_USED_TYPE },
          })
        );
        comp.registerForm.patchValue({
          password: 'password',
          confirmPassword: 'password',
        });

        comp.register();
        tick();

        expect(comp.errorEmailExists).toBe(true);
        expect(comp.errorUserExists).toBe(false);
        expect(comp.error).toBe(false);
      })
    ));

    it('should notify of generic error', inject(
      [RegisterService],
      fakeAsync((service: RegisterService) => {
        spyOn(service, 'save').and.returnValue(
          throwError({
            status: 503,
          })
        );
        comp.registerForm.patchValue({
          password: 'password',
          confirmPassword: 'password',
        });

        comp.register();
        tick();

        expect(comp.errorUserExists).toBe(false);
        expect(comp.errorEmailExists).toBe(false);
        expect(comp.error).toBe(true);
      })
    ));
  });
});
