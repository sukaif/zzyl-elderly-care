package com.zzyl.nursing.mapper;

import java.util.List;
import com.zzyl.nursing.domain.Floor;
import com.zzyl.nursing.domain.Room;
import com.zzyl.nursing.domain.Bed;

/**
 * 楼栋Mapper接口
 *
 * @author ruoyi
 * @date 2026-06-25
 */
public interface FloorMapper
{
    /**
     * 查询楼栋
     *
     * @param id 楼栋主键
     * @return 楼栋
     */
    public Floor selectFloorById(Long id);

    /**
     * 查询楼栋列表
     *
     * @param floor 楼栋
     * @return 楼栋集合
     */
    public List<Floor> selectFloorList(Floor floor);

    /**
     * 查询所有正常状态的楼栋
     *
     * @return 楼栋集合
     */
    public List<Floor> selectAllEnableFloor();

    /**
     * 根据楼栋ID查询房间列表
     *
     * @param floorId 楼栋ID
     * @return 房间集合
     */
    public List<Room> selectRoomListByFloorId(Long floorId);

    /**
     * 根据房间ID查询床位列表
     *
     * @param roomId 房间ID
     * @param status 床位状态（可选过滤）
     * @return 床位集合
     */
    public List<Bed> selectBedListByRoomId(Long roomId, String status);

    /**
     * 新增楼栋
     *
     * @param floor 楼栋
     * @return 结果
     */
    public int insertFloor(Floor floor);

    /**
     * 修改楼栋
     *
     * @param floor 楼栋
     * @return 结果
     */
    public int updateFloor(Floor floor);

    /**
     * 删除楼栋
     *
     * @param id 楼栋主键
     * @return 结果
     */
    public int deleteFloorById(Long id);

    /**
     * 批量删除楼栋
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFloorByIds(Long[] ids);
}
