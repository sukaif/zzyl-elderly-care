import request from '@/utils/request'

export function listNursingPlan(query) {
  return request({
    url: '/nursing/plan/list',
    method: 'get',
    params: query
  })
}

export function getNursingPlan(id) {
  return request({
    url: '/nursing/plan/' + id,
    method: 'get'
  })
}

export function addNursingPlan(data) {
  return request({
    url: '/nursing/plan',
    method: 'post',
    data: data
  })
}

export function updateNursingPlan(data) {
  return request({
    url: '/nursing/plan',
    method: 'put',
    data: data
  })
}

export function delNursingPlan(ids) {
  return request({
    url: '/nursing/plan/' + ids,
    method: 'delete'
  })
}

export function getPlanList() {
  return request({
    url: '/nursing/plan/getPlanList',
    method: 'get'
  })
}
