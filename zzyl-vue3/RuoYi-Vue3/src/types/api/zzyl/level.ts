import type { PageDomain, BaseEntity } from "../common";

/** 护理等级配置分页查询参数 */
export interface LevelQueryParams extends PageDomain {
  /** 等级名称 */
  name?: string;
  /** 护理计划ID */
  planId?: number;
  /** 护理费用 */
  fee?: string;
  /** 状态（0：禁用，1：启用） */
  status?: number;
  /** 等级说明 */
  description?: string;
}

/** 护理等级配置信息 */
export interface NursingLevel extends BaseEntity {
  /** 主键ID */
  id?: number;
  /** 等级名称 */
  name?: string;
  /** 护理计划ID */
  planId?: number;
  /** 护理费用 */
  fee?: string;
  /** 状态（0：禁用，1：启用） */
  status?: number;
  /** 等级说明 */
  description?: string;
  /** 备注 */
  remark?: string;
  /** 创建时间 */
  createTime?: string;
  /** 更新时间 */
  updateTime?: string;
  /** 创建人id */
  createBy?: number;
  /** 更新人id */
  updateBy?: number;
}
