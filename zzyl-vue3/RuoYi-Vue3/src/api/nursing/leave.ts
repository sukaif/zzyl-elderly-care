import request from '@/utils/request'

/** 请假管理API */

export function listLeaveRecord(query: any) {
  return request({
    url: '/nursing/leave/list',
    method: 'get',
    params: query
  })
}

export function getLeaveRecord(id: number) {
  return request({
    url: '/nursing/leave/' + id,
    method: 'get'
  })
}

export function addLeaveRecord(data: any) {
  return request({
    url: '/nursing/leave',
    method: 'post',
    data: data
  })
}

export function updateLeaveRecord(data: any) {
  return request({
    url: '/nursing/leave',
    method: 'put',
    data: data
  })
}

export function delLeaveRecord(id: number | number[]) {
  return request({
    url: '/nursing/leave/' + id,
    method: 'delete'
  })
}

/** 审批请假 */
export function approveLeave(id: number) {
  return request({
    url: '/nursing/leave/approve/' + id,
    method: 'put'
  })
}

/** 销假 */
export function completeLeave(id: number) {
  return request({
    url: '/nursing/leave/complete/' + id,
    method: 'put'
  })
}

/** 标记超时未归 */
export function overdueLeave(id: number) {
  return request({
    url: '/nursing/leave/overdue/' + id,
    method: 'put'
  })
}
