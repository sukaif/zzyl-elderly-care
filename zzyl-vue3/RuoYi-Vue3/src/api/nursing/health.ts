import request from '@/utils/request'

export function listHealth(query: any) {
  return request({ url: '/nursing/health/list', method: 'get', params: query })
}

export function getHealth(id: number) {
  return request({ url: '/nursing/health/' + id, method: 'get' })
}

export function addHealth(data: any) {
  return request({ url: '/nursing/health', method: 'post', data })
}

export function delHealth(id: number | number[]) {
  return request({ url: '/nursing/health/' + id, method: 'delete' })
}
