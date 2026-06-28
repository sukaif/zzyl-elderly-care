package com.zzyl.nursing.service;

import java.util.List;
import com.zzyl.nursing.domain.CheckIn;
import com.zzyl.nursing.domain.dto.CheckInDto;
import com.zzyl.nursing.domain.vo.CheckInDetailVo;
import com.zzyl.nursing.domain.vo.CheckInListVo;

/**
 * 入住Service接口
 *
 * @author ruoyi
 * @date 2026-06-25
 */
public interface ICheckInService
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
     * 新增入住（涉及老人表、入住表、入住费用表、合同表、床位表共5张表）
     *
     * @param checkInDto 入住DTO
     * @return 结果
     */
    public int insertCheckIn(CheckInDto checkInDto);

    /**
     * 修改入住
     *
     * @param checkIn 入住
     * @return 结果
     */
    public int updateCheckIn(CheckIn checkIn);

    /**
     * 查询入住详情
     *
     * @param id 入住ID
     * @return 入住详情VO
     */
    public CheckInDetailVo selectCheckInDetailById(Long id);

    /**
     * 批量删除入住
     *
     * @param ids 需要删除的入住主键集合
     * @return 结果
     */
    public int deleteCheckInByIds(Long[] ids);

    /**
     * 删除入住信息
     *
     * @param id 入住主键
     * @return 结果
     */
    public int deleteCheckInById(Long id);
}
