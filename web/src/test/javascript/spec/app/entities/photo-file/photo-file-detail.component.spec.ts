import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { CechiniTestModule } from '../../../test.module';
import { PhotoFileDetailComponent } from 'app/entities/photo-file/photo-file-detail.component';
import { PhotoFile } from 'app/shared/model/photo-file.model';

describe('Component Tests', () => {
  describe('PhotoFile Management Detail Component', () => {
    let comp: PhotoFileDetailComponent;
    let fixture: ComponentFixture<PhotoFileDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ photoFile: new PhotoFile(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CechiniTestModule],
        declarations: [PhotoFileDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PhotoFileDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PhotoFileDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load photoFile on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.photoFile).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
