package com.zzyl.nursing.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zzyl.common.annotation.Excel;
import com.zzyl.common.core.domain.BaseEntity;

/**
 * 知情告示记录对象 notice_record
 *
 * @author ruoyi
 * @date 2026-06-26
 */
public class NoticeRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 老人ID */
    @Excel(name = "老人ID")
    private Long elderId;

    /** 家属姓名 */
    @Excel(name = "家属姓名")
    private String familyName;

    /** 沟通方式（0电话 1面谈 2微信 3其他） */
    @Excel(name = "沟通方式", readConverterExp = "0=电话,1=面谈,2=微信,3=其他")
    private String communicationType;

    /** 沟通时间 */
    @Excel(name = "沟通时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date communicationTime;

    /** 沟通原因 */
    @Excel(name = "沟通原因")
    private String reason;

    /** 沟通结果 */
    @Excel(name = "沟通结果")
    private String result;

    /** 沟通人（员工） */
    @Excel(name = "沟通人")
    private String communicator;

    /** ===== 以下字段用于查询展示 ===== */
    private String elderName;

    public void setId(Long id) { this.id = id; }
    public Long getId() { return id; }
    public void setElderId(Long elderId) { this.elderId = elderId; }
    public Long getElderId() { return elderId; }
    public void setFamilyName(String familyName) { this.familyName = familyName; }
    public String getFamilyName() { return familyName; }
    public void setCommunicationType(String communicationType) { this.communicationType = communicationType; }
    public String getCommunicationType() { return communicationType; }
    public void setCommunicationTime(Date communicationTime) { this.communicationTime = communicationTime; }
    public Date getCommunicationTime() { return communicationTime; }
    public void setReason(String reason) { this.reason = reason; }
    public String getReason() { return reason; }
    public void setResult(String result) { this.result = result; }
    public String getResult() { return result; }
    public void setCommunicator(String communicator) { this.communicator = communicator; }
    public String getCommunicator() { return communicator; }

    public String getElderName() { return elderName; }
    public void setElderName(String elderName) { this.elderName = elderName; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("elderId", getElderId())
            .append("familyName", getFamilyName())
            .append("communicationType", getCommunicationType())
            .append("communicationTime", getCommunicationTime())
            .append("reason", getReason())
            .append("result", getResult())
            .append("communicator", getCommunicator())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
