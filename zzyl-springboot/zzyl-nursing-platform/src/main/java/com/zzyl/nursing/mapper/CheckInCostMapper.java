package com.zzyl.nursing.mapper;

import java.util.List;
import com.zzyl.nursing.domain.CheckInCost;

/**
 * 入住费用Mapper接口
 *
 * @author ruoyi
 * @date 2026-06-25
 */
public interface CheckInCostMapper
{
    /**
     * 查询入住费用
     *
     * @param id 入住费用主键
     * @return 入住费用
     */
    public CheckInCost selectCheckInCostById(Long id);

    /**
     * 查询入住费用列表
     *
     * @param checkInCost 入住费用
     * @return 入住费用集合
     */
    public List<CheckInCost> selectCheckInCostList(CheckInCost checkInCost);

    /**
     * 根据入住ID查询费用
     *
     * @param checkInId 入住ID
     * @return 入住费用
     */
    public CheckInCost selectCheckInCostByCheckInId(Long checkInId);

    /**
     * 新增入住费用
     *
     * @param checkInCost 入住费用
     * @return 结果
     */
    public int insertCheckInCost(CheckInCost checkInCost);

    /**
     * 修改入住费用
     *
     * @param checkInCost 入住费用
     * @return 结果
     */
    public int updateCheckInCost(CheckInCost checkInCost);

    /**
     * 删除入住费用
     *
     * @param id 入住费用主键
     * @return 结果
     */
    public int deleteCheckInCostById(Long id);

    /**
     * 批量删除入住费用
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCheckInCostByIds(Long[] ids);
}
