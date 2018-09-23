import {Interaction} from "./interaction";

export class Campaign {
  constructor(campaign?: Campaign) {
    this.id           = campaign && campaign.id || null;
    this.name         = campaign && campaign.name || null;
    this.startDate    = campaign && campaign.startDate || null;
    this.endDate      = campaign && campaign.endDate || null;
    this.currency     = campaign && campaign.currency || null;
    this.interactions = campaign && campaign.interactions || null;
  }

  id:           number;
  name:         string;
  startDate:    Date;
  endDate:      Date;
  price:        number;
  currency:     string;
  interactions: Array<Interaction> = [];
}
