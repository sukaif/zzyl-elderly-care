package com.zzyl.nursing.mapper;

import com.zzyl.nursing.domain.LeaveRecord;
import java.util.List;

/**
 * 请假记录Mapper接口
 *
 * @author ruoyi
 * @date 2026-06-26
 */
public interface LeaveRecordMapper
{
    LeaveRecord selectLeaveRecordById(Long id);

    List<LeaveRecord> selectLeaveRecordList(LeaveRecord leaveRecord);

    int insertLeaveRecord(LeaveRecord leaveRecord);

    int updateLeaveRecord(LeaveRecord leaveRecord);

    int deleteLeaveRecordById(Long id);

    int deleteLeaveRecordByIds(Long[] ids);
}
