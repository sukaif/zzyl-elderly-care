import request from '@/utils/request'
import type { AjaxResult, TableDataInfo, CheckInQueryParams, CheckInListVo, CheckInDto, CheckInDetailVo, TreeVo, RoomVo, NursingLevel } from '@/types'

/**
 * 1. 分页查询入住列表
 */
export function listCheckIn(query: CheckInQueryParams): Promise<TableDataInfo<CheckInListVo[]>> {
  return request({
    url: '/nursing/checkin/list',
    method: 'get',
    params: query
  })
}

/**
 * 2. 查询所有护理等级（下拉框用）
 */
export function listNursingLevelAll(): Promise<AjaxResult<NursingLevel[]>> {
  return request({
    url: '/nursing/level/list',
    method: 'get',
    params: { pageNum: 1, pageSize: 999 }
  })
}

/**
 * 3. 根据床位状态查询楼栋-房间-床位树
 * @param status 床位状态（0空闲，传空查全部）
 */
export function getFloorTree(status?: string): Promise<AjaxResult<TreeVo[]>> {
  return request({
    url: '/nursing/floor/tree',
    method: 'get',
    params: status ? { status } : {}
  })
}

/**
 * 4. 根据床位ID查询房间数据
 */
export function getRoomInfoByBedId(bedId: number): Promise<AjaxResult<RoomVo>> {
  return request({
    url: '/nursing/room/bed/' + bedId,
    method: 'get'
  })
}

/**
 * 5. 新增入住
 */
export function addCheckIn(data: CheckInDto): Promise<AjaxResult> {
  return request({
    url: '/nursing/checkin',
    method: 'post',
    data: data
  })
}

/**
 * 6. 查询入住详情
 */
export function getCheckInDetail(id: number): Promise<AjaxResult<CheckInDetailVo>> {
  return request({
    url: '/nursing/checkin/' + id,
    method: 'get'
  })
}

/**
 * 修改入住
 */
export function updateCheckIn(data: any): Promise<AjaxResult> {
  return request({
    url: '/nursing/checkin',
    method: 'put',
    data: data
  })
}

/**
 * 删除入住
 */
export function delCheckIn(id: number | number[]): Promise<AjaxResult> {
  return request({
    url: '/nursing/checkin/' + id,
    method: 'delete'
  })
}
