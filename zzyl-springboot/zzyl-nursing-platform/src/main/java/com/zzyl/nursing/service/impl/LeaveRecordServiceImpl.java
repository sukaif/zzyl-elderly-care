package com.zzyl.nursing.service.impl;

import com.zzyl.common.utils.DateUtils;
import com.zzyl.nursing.domain.LeaveRecord;
import com.zzyl.nursing.mapper.LeaveRecordMapper;
import com.zzyl.nursing.service.ILeaveRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 请假记录Service业务层处理
 *
 * @author ruoyi
 * @date 2026-06-26
 */
@Service
public class LeaveRecordServiceImpl implements ILeaveRecordService
{
    @Autowired
    private LeaveRecordMapper leaveRecordMapper;

    @Override
    public LeaveRecord selectLeaveRecordById(Long id)
    {
        return leaveRecordMapper.selectLeaveRecordById(id);
    }

    @Override
    public List<LeaveRecord> selectLeaveRecordList(LeaveRecord leaveRecord)
    {
        return leaveRecordMapper.selectLeaveRecordList(leaveRecord);
    }

    @Override
    @Transactional
    public int insertLeaveRecord(LeaveRecord leaveRecord)
    {
        leaveRecord.setCreateTime(DateUtils.getNowDate());
        leaveRecord.setStatus("0"); // 待审批
        return leaveRecordMapper.insertLeaveRecord(leaveRecord);
    }

    @Override
    @Transactional
    public int updateLeaveRecord(LeaveRecord leaveRecord)
    {
        leaveRecord.setUpdateTime(DateUtils.getNowDate());
        return leaveRecordMapper.updateLeaveRecord(leaveRecord);
    }

    @Override
    @Transactional
    public int deleteLeaveRecordByIds(Long[] ids)
    {
        return leaveRecordMapper.deleteLeaveRecordByIds(ids);
    }

    @Override
    @Transactional
    public int approveLeave(Long id, String approver)
    {
        LeaveRecord record = new LeaveRecord();
        record.setId(id);
        record.setStatus("1"); // 已审批
        record.setApprover(approver);
        record.setApproveTime(new Date());
        record.setUpdateTime(DateUtils.getNowDate());
        return leaveRecordMapper.updateLeaveRecord(record);
    }

    @Override
    @Transactional
    public int completeLeave(Long id)
    {
        LeaveRecord record = new LeaveRecord();
        record.setId(id);
        record.setStatus("2"); // 已销假
        record.setActualReturnTime(new Date());
        record.setUpdateTime(DateUtils.getNowDate());
        return leaveRecordMapper.updateLeaveRecord(record);
    }

    @Override
    @Transactional
    public int markOverdue(Long id)
    {
        LeaveRecord record = new LeaveRecord();
        record.setId(id);
        record.setStatus("3"); // 超时未归
        record.setUpdateTime(DateUtils.getNowDate());
        return leaveRecordMapper.updateLeaveRecord(record);
    }
}
