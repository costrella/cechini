import { IChartDetail01 } from './chart-detail01.model';

export interface IChart01 {
  monthsName?: string[];
  details?: IChartDetail01[];
}

export class Chart01 implements IChart01 {
  constructor(public monthsName?: string[], public details?: IChartDetail01[]) {}
}
