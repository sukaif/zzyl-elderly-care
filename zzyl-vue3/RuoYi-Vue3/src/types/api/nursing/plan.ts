import type { PageDomain, BaseEntity } from "../common";

export interface ProjectPlan {
  id?: number;
  planId?: number;
  projectId?: number;
  executeTime?: string;
  executeCycle?: string;
  executeFrequency?: string;
}

export interface PlanQueryParams extends PageDomain {
  planName?: string;
  status?: string;
}

export interface NursingPlan extends BaseEntity {
  id?: number;
  sortNo?: number;
  planName?: string;
  status?: number;
  remark?: string;
  projectPlans?: ProjectPlan[];
}