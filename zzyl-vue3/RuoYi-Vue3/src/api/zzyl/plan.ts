import request from '@/utils/request'
import type { AjaxResult, TableDataInfo, PlanQueryParams, NursingPlan } from '@/types'

// 查询护理计划列表
export function listPlan(query: PlanQueryParams): Promise<TableDataInfo<NursingPlan[]>> {
  return request({
    url: '/zzyl/plan/list',
    method: 'get',
    params: query
  })
}

// 查询护理计划详细
export function getPlan(id: number): Promise<AjaxResult<NursingPlan>> {
  return request({
    url: '/zzyl/plan/' + id,
    method: 'get'
  })
}

// 新增护理计划
export function addPlan(data: NursingPlan): Promise<AjaxResult> {
  return request({
    url: '/zzyl/plan',
    method: 'post',
    data: data
  })
}

// 修改护理计划
export function updatePlan(data: NursingPlan): Promise<AjaxResult> {
  return request({
    url: '/zzyl/plan',
    method: 'put',
    data: data
  })
}

// 删除护理计划
export function delPlan(id: number | number[]): Promise<AjaxResult> {
  return request({
    url: '/zzyl/plan/' + id,
    method: 'delete'
  })
}


