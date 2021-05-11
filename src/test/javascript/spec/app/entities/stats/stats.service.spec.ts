import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { StatsService } from 'app/entities/stats/stats.service';
import { IStats, Stats } from 'app/shared/model/stats.model';

describe('Service Tests', () => {
  describe('Stats Service', () => {
    let injector: TestBed;
    let service: StatsService;
    let httpMock: HttpTestingController;
    let elemDefault: IStats;
    let expectedResult: IStats | IStats[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(StatsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Stats(0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should return a list of Stats', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
