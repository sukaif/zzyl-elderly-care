package com.zzyl.nursing.service;

import java.util.List;
import com.zzyl.nursing.domain.Floor;
import com.zzyl.nursing.domain.vo.TreeVo;

/**
 * 楼栋Service接口
 *
 * @author ruoyi
 * @date 2026-06-25
 */
public interface IFloorService
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
     * 根据床位状态查询楼栋-房间-床位树形数据
     *
     * @param status 床位状态（为空查全部）
     * @return 树形数据
     */
    public List<TreeVo> selectFloorTreeByBedStatus(String status);

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
     * 批量删除楼栋
     *
     * @param ids 需要删除的楼栋主键集合
     * @return 结果
     */
    public int deleteFloorByIds(Long[] ids);

    /**
     * 删除楼栋信息
     *
     * @param id 楼栋主键
     * @return 结果
     */
    public int deleteFloorById(Long id);
}
