import request from '@/utils/request'

/** 退住管理API */

export function listCheckOut(query: any) {
  return request({
    url: '/nursing/checkout/list',
    method: 'get',
    params: query
  })
}

export function getCheckOut(id: number) {
  return request({
    url: '/nursing/checkout/' + id,
    method: 'get'
  })
}

export function addCheckOut(data: any) {
  return request({
    url: '/nursing/checkout',
    method: 'post',
    data: data
  })
}

export function updateCheckOut(data: any) {
  return request({
    url: '/nursing/checkout',
    method: 'put',
    data: data
  })
}

export function delCheckOut(id: number | number[]) {
  return request({
    url: '/nursing/checkout/' + id,
    method: 'delete'
  })
}

/** 根据入住ID+离院日期 计算结算费用 */
export function calcSettlement(checkInId: number, leaveDate: string) {
  return request({
    url: '/nursing/stay/calcSettlement',
    method: 'get',
    params: { checkInId, leaveDate }
  })
}
