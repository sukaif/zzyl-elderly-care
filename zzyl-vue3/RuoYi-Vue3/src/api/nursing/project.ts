import request from '@/utils/request'
import type { AjaxResult, TableDataInfo, ProjectQueryParams, NursingProject, NursingProjectOption } from '@/types'

// 查询护理项目列表
export function listProject(query: ProjectQueryParams): Promise<TableDataInfo<NursingProject[]>> {
  return request({
    url: '/nursing/project/list',
    method: 'get',
    params: query
  })
}

// 查询护理项目详细
export function getProject(id: number): Promise<AjaxResult<NursingProject>> {
  return request({
    url: '/nursing/project/' + id,
    method: 'get'
  })
}

// 新增护理项目
export function addProject(data: NursingProject): Promise<AjaxResult> {
  return request({
    url: '/nursing/project',
    method: 'post',
    data: data
  })
}

// 修改护理项目
export function updateProject(data: NursingProject): Promise<AjaxResult> {
  return request({
    url: '/nursing/project',
    method: 'put',
    data: data
  })
}

// 删除护理项目
export function delProject(id: number | number[]): Promise<AjaxResult> {
  return request({
    url: '/nursing/project/' + id,
    method: 'delete'
  })
}

// 查询所有护理项目（下拉框用）
export function getAllProjects(): Promise<AjaxResult<NursingProjectOption[]>> {
  return request({
    url: '/nursing/project/all',
    method: 'get'
  })
}


