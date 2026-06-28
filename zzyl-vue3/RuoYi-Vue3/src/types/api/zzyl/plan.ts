import type { PageDomain, BaseEntity } from "../common";

/** 护理计划配置分页查询参数 */
export interface PlanQueryParams extends PageDomain {
  /** 排序号 */
  sortNo?: number;
  /** 名称 */
  planName?: string;
}

/** 护理计划配置信息 */
export interface NursingPlan extends BaseEntity {
  /** 编号 */
  id?: number;
  /** 排序号 */
  sortNo?: number;
  /** 名称 */
  planName?: string;
  /** 状态 0禁用 1启用 */
  status?: number;
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
