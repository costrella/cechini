import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CechiniTestModule } from '../../../test.module';
import { PhotoFileUpdateComponent } from 'app/entities/photo-file/photo-file-update.component';
import { PhotoFileService } from 'app/entities/photo-file/photo-file.service';
import { PhotoFile } from 'app/shared/model/photo-file.model';

describe('Component Tests', () => {
  describe('PhotoFile Management Update Component', () => {
    let comp: PhotoFileUpdateComponent;
    let fixture: ComponentFixture<PhotoFileUpdateComponent>;
    let service: PhotoFileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CechiniTestModule],
        declarations: [PhotoFileUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PhotoFileUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PhotoFileUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PhotoFileService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PhotoFile(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new PhotoFile();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
