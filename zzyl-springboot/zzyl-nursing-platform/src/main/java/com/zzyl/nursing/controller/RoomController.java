package com.zzyl.nursing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zzyl.common.core.controller.BaseController;
import com.zzyl.common.core.domain.AjaxResult;
import com.zzyl.nursing.domain.vo.RoomVo;
import com.zzyl.nursing.service.IRoomService;

/**
 * 房间Controller
 * 提供根据床位查询房间数据接口
 *
 * @author ruoyi
 * @date 2026-06-25
 */
@RestController
@RequestMapping("/nursing/room")
public class RoomController extends BaseController
{
    @Autowired
    private IRoomService roomService;

    /**
     * 4. 根据床位ID查询房间数据（楼栋名称、房间号、床位号、价格）
     */
    @GetMapping("/bed/{bedId}")
    public AjaxResult getRoomInfoByBedId(@PathVariable("bedId") Long bedId)
    {
        RoomVo roomVo = roomService.selectRoomVoByBedId(bedId);
        return success(roomVo);
    }
}
