package com.zzyl.nursing.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.zzyl.common.core.controller.BaseController;
import com.zzyl.common.core.domain.AjaxResult;
import com.zzyl.nursing.domain.vo.TreeVo;
import com.zzyl.nursing.service.IFloorService;

/**
 * 楼栋Controller
 * 提供楼栋-房间-床位树形数据查询接口
 *
 * @author ruoyi
 * @date 2026-06-25
 */
@RestController
@RequestMapping("/nursing/floor")
public class FloorController extends BaseController
{
    @Autowired
    private IFloorService floorService;

    /**
     * 3. 根据床位状态查询楼栋-房间-床位树形数据
     * 参数：status（床位状态，0空闲 传空查全部）
     */
    @GetMapping("/tree")
    public AjaxResult tree(@RequestParam(value = "status", required = false) String status)
    {
        List<TreeVo> list = floorService.selectFloorTreeByBedStatus(status);
        return success(list);
    }
}
