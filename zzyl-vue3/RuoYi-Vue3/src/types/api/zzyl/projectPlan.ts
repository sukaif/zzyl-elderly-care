import type { PageDomain, BaseEntity } from "../common";

/** 护理计划和项目关联配置分页查询参数 */
export interface ProjectPlanQueryParams extends PageDomain {
  /** 计划id */
  planId?: number;
  /** 项目id */
  projectId?: number;
  /** 计划执行时间 */
  executeTime?: string;
  /** 执行周期 0 天 1 周 2月 */
  executeCycle?: number;
  /** 执行频次 */
  executeFrequency?: number;
}

/** 护理计划和项目关联配置信息 */
export interface NursingProjectPlan extends BaseEntity {
  /** ${column.columnComment} */
  id?: number;
  /** 计划id */
  planId?: number;
  /** 项目id */
  projectId?: number;
  /** 计划执行时间 */
  executeTime?: string;
  /** 执行周期 0 天 1 周 2月 */
  executeCycle?: number;
  /** 执行频次 */
  executeFrequency?: number;
  /** 创建时间 */
  createTime?: string;
  /** 更新时间 */
  updateTime?: string;
  /** 创建人id */
  createBy?: number;
  /** 更新人id */
  updateBy?: number;
  /** 备注 */
  remark?: string;
}
