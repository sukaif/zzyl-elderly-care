package com.zzyl.nursing.service;

import java.util.List;
import com.zzyl.nursing.domain.Room;
import com.zzyl.nursing.domain.vo.RoomVo;

/**
 * 房间Service接口
 *
 * @author ruoyi
 * @date 2026-06-25
 */
public interface IRoomService
{
    /**
     * 查询房间
     *
     * @param id 房间主键
     * @return 房间
     */
    public Room selectRoomById(Long id);

    /**
     * 查询房间列表
     *
     * @param room 房间
     * @return 房间集合
     */
    public List<Room> selectRoomList(Room room);

    /**
     * 根据床位ID查询房间数据（楼层、房间、价格）
     *
     * @param bedId 床位ID
     * @return 房间数据VO
     */
    public RoomVo selectRoomVoByBedId(Long bedId);

    /**
     * 新增房间
     *
     * @param room 房间
     * @return 结果
     */
    public int insertRoom(Room room);

    /**
     * 修改房间
     *
     * @param room 房间
     * @return 结果
     */
    public int updateRoom(Room room);

    /**
     * 批量删除房间
     *
     * @param ids 需要删除的房间主键集合
     * @return 结果
     */
    public int deleteRoomByIds(Long[] ids);

    /**
     * 删除房间信息
     *
     * @param id 房间主键
     * @return 结果
     */
    public int deleteRoomById(Long id);
}
