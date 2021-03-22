import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { WarehouseService } from 'app/entities/warehouse/warehouse.service';
import { IWarehouse, Warehouse } from 'app/shared/model/warehouse.model';
import { OrderFileType } from 'app/shared/model/enumerations/order-file-type.model';

describe('Service Tests', () => {
  describe('Warehouse Service', () => {
    let injector: TestBed;
    let service: WarehouseService;
    let httpMock: HttpTestingController;
    let elemDefault: IWarehouse;
    let expectedResult: IWarehouse | IWarehouse[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(WarehouseService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Warehouse(0, 'AAAAAAA', 'AAAAAAA', OrderFileType.EXCEL);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Warehouse', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Warehouse()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Warehouse', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            mail: 'BBBBBB',
            orderFileType: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Warehouse', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            mail: 'BBBBBB',
            orderFileType: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Warehouse', () => {
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
