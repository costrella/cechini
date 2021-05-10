import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CechiniTestModule } from '../../../test.module';
import { StatsComponent } from 'app/entities/stats/stats.component';
import { StatsService } from 'app/entities/stats/stats.service';
import { Stats } from 'app/shared/model/stats.model';

describe('Component Tests', () => {
  describe('Stats Management Component', () => {
    let comp: StatsComponent;
    let fixture: ComponentFixture<StatsComponent>;
    let service: StatsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CechiniTestModule],
        declarations: [StatsComponent],
      })
        .overrideTemplate(StatsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StatsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StatsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Stats(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.stats && comp.stats[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
