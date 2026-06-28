import request from '@/utils/request'
import type { AjaxResult, TableDataInfo, LevelQueryParams, NursingLevel, NursingPlan } from '@/types'

export function listNursingLevel(query: LevelQueryParams): Promise<TableDataInfo<NursingLevel[]>> {
  return request({
    url: '/nursing/level/list',
    method: 'get',
    params: query
  })
}

export function getEnablePlanList(): Promise<AjaxResult<NursingPlan[]>> {
  return request({
    url: '/nursing/level/getEnablePlanList',
    method: 'get'
  })
}

export function getNursingLevel(id: number): Promise<AjaxResult<NursingLevel>> {
  return request({
    url: '/nursing/level/' + id,
    method: 'get'
  })
}

export function addNursingLevel(data: NursingLevel): Promise<AjaxResult> {
  return request({
    url: '/nursing/level',
    method: 'post',
    data: data
  })
}

export function updateNursingLevel(data: NursingLevel): Promise<AjaxResult> {
  return request({
    url: '/nursing/level',
    method: 'put',
    data: data
  })
}

export function delNursingLevel(id: number | number[]): Promise<AjaxResult> {
  return request({
    url: '/nursing/level/' + id,
    method: 'delete'
  })
}
