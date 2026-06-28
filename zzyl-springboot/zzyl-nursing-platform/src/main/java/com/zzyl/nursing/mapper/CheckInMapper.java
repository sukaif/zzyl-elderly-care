package com.zzyl.nursing.mapper;

import java.util.List;
import java.util.Map;
import com.zzyl.nursing.domain.CheckIn;
import com.zzyl.nursing.domain.vo.CheckInListVo;
import org.apache.ibatis.annotations.Param;

/**
 * 入住Mapper接口
 *
 * @author ruoyi
 * @date 2026-06-25
 */
public interface CheckInMapper
{
    /**
     * 查询入住
     *
     * @param id 入住主键
     * @return 入住
     */
    public CheckIn selectCheckInById(Long id);

    /**
     * 查询入住列表
     *
     * @param checkIn 入住
     * @return 入住集合
     */
    public List<CheckIn> selectCheckInList(CheckIn checkIn);

    /**
     * 分页查询入住列表（关联老人、护理等级）
     *
     * @param checkIn 入住（含查询参数）
     * @return 入住列表VO
     */
    public List<CheckInListVo> selectCheckInListVo(CheckIn checkIn);

    /**
     * 新增入住
     *
     * @param checkIn 入住
     * @return 结果
     */
    public int insertCheckIn(CheckIn checkIn);

    /**
     * 修改入住
     *
     * @param checkIn 入住
     * @return 结果
     */
    public int updateCheckIn(CheckIn checkIn);

    /**
     * 删除入住
     *
     * @param id 入住主键
     * @return 结果
     */
    public int deleteCheckInById(Long id);

    /**
     * 批量删除入住
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCheckInByIds(Long[] ids);

    // ========== 在住管理统计 ==========

    /** 统计总床位数 */
    public int countTotalBeds();

    /** 按床位状态统计数量 */
    public int countBedsByStatus(String status);

    /** 统计今日入住人数 */
    public int countTodayCheckIn();

    /** 统计今日退住人数 */
    public int countTodayCheckOut();

    /** 统计当前请假人数 */
    public int countOnLeave();

    /** 按护理等级统计入住人数 */
    public List<Map<String, Object>> countByNursingLevel();

    /** 统计指定日期范围内的入住人数 */
    public int countCheckInByDateRange(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /** 统计指定日期范围内的退住人数 */
    public int countCheckOutByDateRange(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /** 统计指定日期范围内的请假人次 */
    public int countLeaveByDateRange(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
