import request from '@/utils/request'
import type { AjaxResult, TableDataInfo, PlanQueryParams, NursingPlan } from '@/types'

export function listNursingPlan(query: PlanQueryParams): Promise<TableDataInfo<NursingPlan[]>> {
  return request({
    url: '/nursing/plan/list',
    method: 'get',
    params: query
  })
}

export function getNursingPlan(id: number): Promise<AjaxResult<NursingPlan>> {
  return request({
    url: '/nursing/plan/' + id,
    method: 'get'
  })
}

export function addNursingPlan(data: NursingPlan): Promise<AjaxResult> {
  return request({
    url: '/nursing/plan',
    method: 'post',
    data: data
  })
}

export function updateNursingPlan(data: NursingPlan): Promise<AjaxResult> {
  return request({
    url: '/nursing/plan',
    method: 'put',
    data: data
  })
}

export function delNursingPlan(id: number | number[]): Promise<AjaxResult> {
  return request({
    url: '/nursing/plan/' + id,
    method: 'delete'
  })
}

export function selectAllEnablePlan(): Promise<AjaxResult<NursingPlan[]>> {
  return request({
    url: '/nursing/plan/getPlanList',
    method: 'get'
  })
}
