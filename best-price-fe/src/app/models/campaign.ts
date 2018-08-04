import {Interaction} from "./interaction";

export class Campaign {
  id:           number;
  name:         string;
  startDate:    Date;
  endDate:      Date;
  price:        number;
  currency:     string;
  interactions: Array<Interaction> = [];
}
