package com.zzyl.nursing.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.zzyl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zzyl.nursing.mapper.FloorMapper;
import com.zzyl.nursing.domain.Floor;
import com.zzyl.nursing.domain.Room;
import com.zzyl.nursing.domain.Bed;
import com.zzyl.nursing.domain.vo.TreeVo;
import com.zzyl.nursing.service.IFloorService;

/**
 * 楼栋Service业务层处理
 *
 * @author ruoyi
 * @date 2026-06-25
 */
@Service
public class FloorServiceImpl implements IFloorService
{
    @Autowired
    private FloorMapper floorMapper;

    @Override
    public Floor selectFloorById(Long id)
    {
        return floorMapper.selectFloorById(id);
    }

    @Override
    public List<Floor> selectFloorList(Floor floor)
    {
        return floorMapper.selectFloorList(floor);
    }

    /**
     * 根据床位状态查询楼栋-房间-床位树形数据
     *
     * @param status 床位状态（为空查询所有床位）
     * @return 树形结构数据
     */
    @Override
    public List<TreeVo> selectFloorTreeByBedStatus(String status)
    {
        List<TreeVo> result = new ArrayList<>();

        // 1. 查询所有启用的楼栋
        List<Floor> floors = floorMapper.selectAllEnableFloor();

        for (Floor floor : floors) {
            TreeVo floorNode = new TreeVo();
            floorNode.setId(floor.getId());
            floorNode.setLabel(floor.getName());
            floorNode.setType("floor");

            // 2. 查询楼栋下的所有房间
            List<Room> rooms = floorMapper.selectRoomListByFloorId(floor.getId());
            List<TreeVo> roomNodes = new ArrayList<>();

            for (Room room : rooms) {
                TreeVo roomNode = new TreeVo();
                roomNode.setId(room.getId());
                roomNode.setLabel(room.getRoomNumber());
                roomNode.setType("room");

                // 3. 查询房间下的床位（按状态过滤）
                List<Bed> beds = floorMapper.selectBedListByRoomId(room.getId(), status);
                List<TreeVo> bedNodes = new ArrayList<>();

                for (Bed bed : beds) {
                    TreeVo bedNode = new TreeVo();
                    bedNode.setId(bed.getId());
                    bedNode.setLabel(bed.getBedNumber());
                    bedNode.setType("bed");
                    bedNode.setStatus(bed.getStatus());
                    bedNode.setPrice(bed.getPrice() != null ? bed.getPrice().toString() : "0");
                    bedNodes.add(bedNode);
                }

                roomNode.setChildren(bedNodes);
                roomNodes.add(roomNode);
            }

            floorNode.setChildren(roomNodes);
            result.add(floorNode);
        }

        return result;
    }

    @Override
    public int insertFloor(Floor floor)
    {
        floor.setCreateTime(DateUtils.getNowDate());
        return floorMapper.insertFloor(floor);
    }

    @Override
    public int updateFloor(Floor floor)
    {
        floor.setUpdateTime(DateUtils.getNowDate());
        return floorMapper.updateFloor(floor);
    }

    @Override
    public int deleteFloorByIds(Long[] ids)
    {
        return floorMapper.deleteFloorByIds(ids);
    }

    @Override
    public int deleteFloorById(Long id)
    {
        return floorMapper.deleteFloorById(id);
    }
}
