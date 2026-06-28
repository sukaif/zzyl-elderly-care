package com.zzyl.nursing.service;

import com.zzyl.nursing.domain.LeaveRecord;
import java.util.List;

/**
 * 请假记录Service接口
 *
 * @author ruoyi
 * @date 2026-06-26
 */
public interface ILeaveRecordService
{
    LeaveRecord selectLeaveRecordById(Long id);

    List<LeaveRecord> selectLeaveRecordList(LeaveRecord leaveRecord);

    int insertLeaveRecord(LeaveRecord leaveRecord);

    int updateLeaveRecord(LeaveRecord leaveRecord);

    int deleteLeaveRecordByIds(Long[] ids);

    /** 审批通过 */
    int approveLeave(Long id, String approver);

    /** 销假 */
    int completeLeave(Long id);

    /** 标记超时未归 */
    int markOverdue(Long id);
}
