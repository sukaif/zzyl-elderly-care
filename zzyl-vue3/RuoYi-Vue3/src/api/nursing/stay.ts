import request from '@/utils/request'

/** 在住管理API */

/** 1. 获取房态统计数据 */
export function getRoomStatus() {
  return request({
    url: '/nursing/stay/roomStatus',
    method: 'get'
  })
}

/** 2. 在住老人列表 */
export function listResident(query: any) {
  return request({
    url: '/nursing/stay/residentList',
    method: 'get',
    params: query
  })
}

/** 3. 流动统计 */
export function getFlowStats(startDate?: string, endDate?: string) {
  return request({
    url: '/nursing/stay/flowStats',
    method: 'get',
    params: { startDate, endDate }
  })
}

/** 4. 知情告示列表 */
export function listNoticeRecord(query: any) {
  return request({
    url: '/nursing/stay/noticeRecord/list',
    method: 'get',
    params: query
  })
}

/** 5. 新增知情告示 */
export function addNoticeRecord(data: any) {
  return request({
    url: '/nursing/stay/noticeRecord',
    method: 'post',
    data: data
  })
}

/** 6. 删除知情告示 */
export function delNoticeRecord(id: number | number[]) {
  return request({
    url: '/nursing/stay/noticeRecord/' + id,
    method: 'delete'
  })
}
