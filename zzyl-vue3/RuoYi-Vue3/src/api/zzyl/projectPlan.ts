import request from '@/utils/request'
import type { AjaxResult, TableDataInfo, ProjectPlanQueryParams, NursingProjectPlan } from '@/types'

// 查询护理计划和项目关联列表
export function listProjectPlan(query: ProjectPlanQueryParams): Promise<TableDataInfo<NursingProjectPlan[]>> {
  return request({
    url: '/zzyl/projectPlan/list',
    method: 'get',
    params: query
  })
}

// 查询护理计划和项目关联详细
export function getProjectPlan(id: number): Promise<AjaxResult<NursingProjectPlan>> {
  return request({
    url: '/zzyl/projectPlan/' + id,
    method: 'get'
  })
}

// 新增护理计划和项目关联
export function addProjectPlan(data: NursingProjectPlan): Promise<AjaxResult> {
  return request({
    url: '/zzyl/projectPlan',
    method: 'post',
    data: data
  })
}

// 修改护理计划和项目关联
export function updateProjectPlan(data: NursingProjectPlan): Promise<AjaxResult> {
  return request({
    url: '/zzyl/projectPlan',
    method: 'put',
    data: data
  })
}

// 删除护理计划和项目关联
export function delProjectPlan(id: number | number[]): Promise<AjaxResult> {
  return request({
    url: '/zzyl/projectPlan/' + id,
    method: 'delete'
  })
}


