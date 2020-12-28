import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CechiniTestModule } from '../../../test.module';
import { PhotoFileComponent } from 'app/entities/photo-file/photo-file.component';
import { PhotoFileService } from 'app/entities/photo-file/photo-file.service';
import { PhotoFile } from 'app/shared/model/photo-file.model';

describe('Component Tests', () => {
  describe('PhotoFile Management Component', () => {
    let comp: PhotoFileComponent;
    let fixture: ComponentFixture<PhotoFileComponent>;
    let service: PhotoFileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CechiniTestModule],
        declarations: [PhotoFileComponent],
      })
        .overrideTemplate(PhotoFileComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PhotoFileComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PhotoFileService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PhotoFile(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.photoFiles && comp.photoFiles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
