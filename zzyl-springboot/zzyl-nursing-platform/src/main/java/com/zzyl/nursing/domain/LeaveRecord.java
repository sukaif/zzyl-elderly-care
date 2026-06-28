package com.zzyl.nursing.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zzyl.common.annotation.Excel;
import com.zzyl.common.core.domain.BaseEntity;

/**
 * 请假记录对象 leave_record
 *
 * @author ruoyi
 * @date 2026-06-26
 */
public class LeaveRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 老人ID */
    @Excel(name = "老人ID")
    private Long elderId;

    /** 入住ID */
    @Excel(name = "入住ID")
    private Long checkInId;

    /** 请假类型（0外出探亲 1就医 2回家 3其他） */
    @Excel(name = "请假类型", readConverterExp = "0=外出探亲,1=就医,2=回家,3=其他")
    private String leaveType;

    /** 请假开始时间 */
    @Excel(name = "请假开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 预计返回时间 */
    @Excel(name = "预计返回时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date expectedReturnTime;

    /** 实际返回时间（销假时填写） */
    @Excel(name = "实际返回时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date actualReturnTime;

    /** 请假天数 */
    @Excel(name = "请假天数")
    private Integer leaveDays;

    /** 紧急联系人 */
    @Excel(name = "紧急联系人")
    private String emergencyContact;

    /** 紧急联系人电话 */
    @Excel(name = "紧急联系人电话")
    private String emergencyPhone;

    /** 请假事由 */
    @Excel(name = "请假事由")
    private String reason;

    /** 状态（0待审批 1已审批 2已销假 3超时未归） */
    @Excel(name = "状态", readConverterExp = "0=待审批,1=已审批,2=已销假,3=超时未归")
    private String status;

    /** 审批人 */
    @Excel(name = "审批人")
    private String approver;

    /** 审批时间 */
    @Excel(name = "审批时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date approveTime;

    /** ===== 以下字段用于查询展示 ===== */
    private String elderName;
    private String bedNumber;

    public void setId(Long id) { this.id = id; }
    public Long getId() { return id; }
    public void setElderId(Long elderId) { this.elderId = elderId; }
    public Long getElderId() { return elderId; }
    public void setCheckInId(Long checkInId) { this.checkInId = checkInId; }
    public Long getCheckInId() { return checkInId; }
    public void setLeaveType(String leaveType) { this.leaveType = leaveType; }
    public String getLeaveType() { return leaveType; }
    public void setStartTime(Date startTime) { this.startTime = startTime; }
    public Date getStartTime() { return startTime; }
    public void setExpectedReturnTime(Date expectedReturnTime) { this.expectedReturnTime = expectedReturnTime; }
    public Date getExpectedReturnTime() { return expectedReturnTime; }
    public void setActualReturnTime(Date actualReturnTime) { this.actualReturnTime = actualReturnTime; }
    public Date getActualReturnTime() { return actualReturnTime; }
    public void setLeaveDays(Integer leaveDays) { this.leaveDays = leaveDays; }
    public Integer getLeaveDays() { return leaveDays; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }
    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyPhone(String emergencyPhone) { this.emergencyPhone = emergencyPhone; }
    public String getEmergencyPhone() { return emergencyPhone; }
    public void setReason(String reason) { this.reason = reason; }
    public String getReason() { return reason; }
    public void setStatus(String status) { this.status = status; }
    public String getStatus() { return status; }
    public void setApprover(String approver) { this.approver = approver; }
    public String getApprover() { return approver; }
    public void setApproveTime(Date approveTime) { this.approveTime = approveTime; }
    public Date getApproveTime() { return approveTime; }

    public String getElderName() { return elderName; }
    public void setElderName(String elderName) { this.elderName = elderName; }
    public String getBedNumber() { return bedNumber; }
    public void setBedNumber(String bedNumber) { this.bedNumber = bedNumber; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("elderId", getElderId())
            .append("checkInId", getCheckInId())
            .append("leaveType", getLeaveType())
            .append("startTime", getStartTime())
            .append("expectedReturnTime", getExpectedReturnTime())
            .append("actualReturnTime", getActualReturnTime())
            .append("leaveDays", getLeaveDays())
            .append("emergencyContact", getEmergencyContact())
            .append("emergencyPhone", getEmergencyPhone())
            .append("reason", getReason())
            .append("status", getStatus())
            .append("approver", getApprover())
            .append("approveTime", getApproveTime())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
