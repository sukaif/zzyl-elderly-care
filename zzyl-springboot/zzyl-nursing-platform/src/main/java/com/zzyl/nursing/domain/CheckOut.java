package com.zzyl.nursing.domain;

import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zzyl.common.annotation.Excel;
import com.zzyl.common.core.domain.BaseEntity;

/**
 * 退住记录对象 check_out
 *
 * @author ruoyi
 * @date 2026-06-26
 */
public class CheckOut extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 入住ID */
    @Excel(name = "入住ID")
    private Long checkInId;

    /** 老人ID */
    @Excel(name = "老人ID")
    private Long elderId;

    /** 退住类型（0个人原因 1病情转院 2去世 3其他） */
    @Excel(name = "退住类型", readConverterExp = "0=个人原因,1=病情转院,2=去世,3=其他")
    private String checkoutType;

    /** 申请退住日期 */
    @Excel(name = "申请退住日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date checkoutDate;

    /** 实际离院日期 */
    @Excel(name = "实际离院日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date actualLeaveDate;

    /** 退住原因 */
    @Excel(name = "退住原因")
    private String reason;

    /** 已缴费用合计 */
    @Excel(name = "已缴费用合计")
    private BigDecimal totalCharged;

    /** 应扣费用合计 */
    @Excel(name = "应扣费用合计")
    private BigDecimal totalDeducted;

    /** 应退金额 */
    @Excel(name = "应退金额")
    private BigDecimal refundAmount;

    /** 欠款金额 */
    @Excel(name = "欠款金额")
    private BigDecimal arrears;

    /** 状态（0待结算 1已结算 2已完成） */
    @Excel(name = "状态", readConverterExp = "0=待结算,1=已结算,2=已完成")
    private String status;

    /** ===== 以下字段用于查询展示（非数据库字段） ===== */
    private String elderName;
    private String idCardNo;
    private String phone;
    private String bedNumber;
    private String roomNumber;
    private String floorName;

    public void setId(Long id) { this.id = id; }
    public Long getId() { return id; }
    public void setCheckInId(Long checkInId) { this.checkInId = checkInId; }
    public Long getCheckInId() { return checkInId; }
    public void setElderId(Long elderId) { this.elderId = elderId; }
    public Long getElderId() { return elderId; }
    public void setCheckoutType(String checkoutType) { this.checkoutType = checkoutType; }
    public String getCheckoutType() { return checkoutType; }
    public void setCheckoutDate(Date checkoutDate) { this.checkoutDate = checkoutDate; }
    public Date getCheckoutDate() { return checkoutDate; }
    public void setActualLeaveDate(Date actualLeaveDate) { this.actualLeaveDate = actualLeaveDate; }
    public Date getActualLeaveDate() { return actualLeaveDate; }
    public void setReason(String reason) { this.reason = reason; }
    public String getReason() { return reason; }
    public void setTotalCharged(BigDecimal totalCharged) { this.totalCharged = totalCharged; }
    public BigDecimal getTotalCharged() { return totalCharged; }
    public void setTotalDeducted(BigDecimal totalDeducted) { this.totalDeducted = totalDeducted; }
    public BigDecimal getTotalDeducted() { return totalDeducted; }
    public void setRefundAmount(BigDecimal refundAmount) { this.refundAmount = refundAmount; }
    public BigDecimal getRefundAmount() { return refundAmount; }
    public void setArrears(BigDecimal arrears) { this.arrears = arrears; }
    public BigDecimal getArrears() { return arrears; }
    public void setStatus(String status) { this.status = status; }
    public String getStatus() { return status; }

    public String getElderName() { return elderName; }
    public void setElderName(String elderName) { this.elderName = elderName; }
    public String getIdCardNo() { return idCardNo; }
    public void setIdCardNo(String idCardNo) { this.idCardNo = idCardNo; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getBedNumber() { return bedNumber; }
    public void setBedNumber(String bedNumber) { this.bedNumber = bedNumber; }
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public String getFloorName() { return floorName; }
    public void setFloorName(String floorName) { this.floorName = floorName; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("checkInId", getCheckInId())
            .append("elderId", getElderId())
            .append("checkoutType", getCheckoutType())
            .append("checkoutDate", getCheckoutDate())
            .append("actualLeaveDate", getActualLeaveDate())
            .append("reason", getReason())
            .append("totalCharged", getTotalCharged())
            .append("totalDeducted", getTotalDeducted())
            .append("refundAmount", getRefundAmount())
            .append("arrears", getArrears())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
