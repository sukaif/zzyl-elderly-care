import type { PageDomain, BaseEntity } from "../common";

/** 入住查询参数 */
export interface CheckInQueryParams extends PageDomain {
  elderName?: string;
  idNumber?: string;
  status?: string;
}

/** 入住列表VO */
export interface CheckInListVo {
  id?: number;
  elderId?: number;
  elderName?: string;
  age?: number;
  gender?: string;
  idCardNo?: string;
  phone?: string;
  nursingLevelName?: string;
  status?: string;
  checkInDate?: string;
  bedNumber?: string;
  createTime?: string;
}

/** 新增入住DTO */
export interface CheckInDto {
  /** 老人信息 */
  name?: string;
  age?: number;
  gender?: string;
  idCardNo?: string;
  phone?: string;
  address?: string;
  image?: string;
  /** 家属信息(JSON) */
  familyInfo?: string;
  /** 入住费用 */
  nursingLevelId?: number;
  nursingFee?: number;
  bedId?: number;
  bedPrice?: number;
  prePay?: number;
  /** 合同信息 */
  checkInDate?: string;
  signDate?: string;
  startDate?: string;
  endDate?: string;
}

/** 入住详情VO */
export interface CheckInDetailVo {
  id?: number;
  checkInDate?: string;
  status?: string;
  elderId?: number;
  elderName?: string;
  age?: number;
  gender?: string;
  idCardNo?: string;
  phone?: string;
  address?: string;
  image?: string;
  familyInfo?: string;
  nursingLevelId?: number;
  nursingLevelName?: string;
  bedId?: number;
  bedNumber?: string;
  roomNumber?: string;
  floorName?: string;
  bedPrice?: number;
  nursingFee?: number;
  totalCost?: number;
  prePay?: number;
  contract?: any;
}

/** 树形节点VO */
export interface TreeVo {
  id?: number;
  label?: string;
  type?: string;
  status?: string;
  price?: string;
  children?: TreeVo[];
}

/** 房间数据VO */
export interface RoomVo {
  floorName?: string;
  roomNumber?: string;
  bedNumber?: string;
  price?: number;
}

/** 护理等级下拉选项 */
export interface NursingLevelOption {
  id?: number;
  name?: string;
  fee?: number;
}
