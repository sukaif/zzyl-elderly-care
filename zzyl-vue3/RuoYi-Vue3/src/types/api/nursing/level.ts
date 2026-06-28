import type { PageDomain, BaseEntity } from "../common";
import type { NursingPlan } from "./plan";

export interface LevelQueryParams extends PageDomain {
  name?: string;
  status?: string;
}

export interface NursingLevel extends BaseEntity {
  id?: number;
  name?: string;
  planId?: number;
  fee?: string;
  status?: number;
  description?: string;
  remark?: string;
}

export type NursingPlanOption = Pick<NursingPlan, 'id' | 'planName'>;