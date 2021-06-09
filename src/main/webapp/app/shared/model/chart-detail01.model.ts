export interface IChartDetail01 {
  label: string;
  data: number[];
  lineTension: number;
  backgroundColor: string;
  borderColor: string;
  borderWidth: number;
  pointBackgroundColor: string;
}

export class ChartDetail01 implements IChartDetail01 {
  constructor(
    public label: string,
    public data: number[],
    public lineTension: number,
    public backgroundColor: string,
    public borderColor: string,
    public borderWidth: number,
    public pointBackgroundColor: string
  ) {}
}
