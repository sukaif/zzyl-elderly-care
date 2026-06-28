import type { PageDomain, BaseEntity } from "../common";

/** 护理项目配置分页查询参数 */
export interface ProjectQueryParams extends PageDomain {
  /** 名称 */
  name?: string;
  /** 排序号 */
  orderNo?: number;
  /** 单位 */
  unit?: string;
  /** 价格 */
  price?: string;
  /** 图片 */
  image?: string;
  /** 护理要求 */
  nursingRequirement?: string;
  /** 状态（0：禁用，1：启用） */
  status?: number;
}

/** 护理项目配置信息 */
export interface NursingProject extends BaseEntity {
  /** 编号 */
  id?: number;
  /** 名称 */
  name?: string;
  /** 排序号 */
  orderNo?: number;
  /** 单位 */
  unit?: string;
  /** 价格 */
  price?: string;
  /** 图片 */
  image?: string;
  /** 护理要求 */
  nursingRequirement?: string;
  /** 状态（0：禁用，1：启用） */
  status?: number;
  /** 创建者 */
  createBy?: string;
  /** 创建时间 */
  createTime?: string;
  /** 更新者 */
  updateBy?: string;
  /** 更新时间 */
  updateTime?: string;
  /** 备注 */
  remark?: string;
}
