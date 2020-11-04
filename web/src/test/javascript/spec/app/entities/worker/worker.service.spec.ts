import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { WorkerService } from 'app/entities/worker/worker.service';
import { IWorker, Worker } from 'app/shared/model/worker.model';

describe('Service Tests', () => {
  describe('Worker Service', () => {
    let injector: TestBed;
    let service: WorkerService;
    let httpMock: HttpTestingController;
    let elemDefault: IWorker;
    let expectedResult: IWorker | IWorker[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(WorkerService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Worker(0, 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            hiredDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Worker', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            hiredDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            hiredDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Worker()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Worker', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            surname: 'BBBBBB',
            hiredDate: currentDate.format(DATE_TIME_FORMAT),
            desc: 'BBBBBB',
            phone: 'BBBBBB',
            login: 'BBBBBB',
            password: 'BBBBBB',
            target: 1,
            active: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            hiredDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Worker', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            surname: 'BBBBBB',
            hiredDate: currentDate.format(DATE_TIME_FORMAT),
            desc: 'BBBBBB',
            phone: 'BBBBBB',
            login: 'BBBBBB',
            password: 'BBBBBB',
            target: 1,
            active: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            hiredDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Worker', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
