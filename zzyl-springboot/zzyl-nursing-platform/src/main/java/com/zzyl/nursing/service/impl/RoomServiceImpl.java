package com.zzyl.nursing.service.impl;

import java.util.List;
import com.zzyl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zzyl.nursing.mapper.RoomMapper;
import com.zzyl.nursing.domain.Room;
import com.zzyl.nursing.domain.vo.RoomVo;
import com.zzyl.nursing.service.IRoomService;

/**
 * 房间Service业务层处理
 *
 * @author ruoyi
 * @date 2026-06-25
 */
@Service
public class RoomServiceImpl implements IRoomService
{
    @Autowired
    private RoomMapper roomMapper;

    @Override
    public Room selectRoomById(Long id)
    {
        return roomMapper.selectRoomById(id);
    }

    @Override
    public List<Room> selectRoomList(Room room)
    {
        return roomMapper.selectRoomList(room);
    }

    @Override
    public RoomVo selectRoomVoByBedId(Long bedId)
    {
        return roomMapper.selectRoomVoByBedId(bedId);
    }

    @Override
    public int insertRoom(Room room)
    {
        room.setCreateTime(DateUtils.getNowDate());
        return roomMapper.insertRoom(room);
    }

    @Override
    public int updateRoom(Room room)
    {
        room.setUpdateTime(DateUtils.getNowDate());
        return roomMapper.updateRoom(room);
    }

    @Override
    public int deleteRoomByIds(Long[] ids)
    {
        return roomMapper.deleteRoomByIds(ids);
    }

    @Override
    public int deleteRoomById(Long id)
    {
        return roomMapper.deleteRoomById(id);
    }
}
