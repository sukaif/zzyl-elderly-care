import request from '@/utils/request'

export function listNursingLevel(query) {
  return request({
    url: '/nursing/level/list',
    method: 'get',
    params: query
  })
}

export function getNursingLevel(id) {
  return request({
    url: '/nursing/level/' + id,
    method: 'get'
  })
}

export function addNursingLevel(data) {
  return request({
    url: '/nursing/level',
    method: 'post',
    data: data
  })
}

export function updateNursingLevel(data) {
  return request({
    url: '/nursing/level',
    method: 'put',
    data: data
  })
}

export function delNursingLevel(ids) {
  return request({
    url: '/nursing/level/' + ids,
    method: 'delete'
  })
}

export function getPlanList() {
  return request({
    url: '/nursing/level/getPlanList',
    method: 'get'
  })
}
