package com.zzyl.nursing.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zzyl.common.annotation.Excel;
import com.zzyl.common.core.domain.BaseEntity;

/**
 * 合同对象 contract
 *
 * @author ruoyi
 * @date 2026-06-25
 */
public class Contract extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 合同编号 */
    @Excel(name = "合同编号")
    private String contractNo;

    /** 老人ID */
    @Excel(name = "老人ID")
    private Long elderId;

    /** 入住ID */
    @Excel(name = "入住ID")
    private Long checkInId;

    /** 签约日期 */
    @Excel(name = "签约日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date signDate;

    /** 合同开始日期 */
    @Excel(name = "合同开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 合同结束日期 */
    @Excel(name = "合同结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /** 合同内容 */
    private String content;

    /** 状态（0待签署 1已签署 2已到期） */
    @Excel(name = "状态", readConverterExp = "0=待签署,1=已签署,2=已到期")
    private String status;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setContractNo(String contractNo)
    {
        this.contractNo = contractNo;
    }

    public String getContractNo()
    {
        return contractNo;
    }

    public void setElderId(Long elderId)
    {
        this.elderId = elderId;
    }

    public Long getElderId()
    {
        return elderId;
    }

    public void setCheckInId(Long checkInId)
    {
        this.checkInId = checkInId;
    }

    public Long getCheckInId()
    {
        return checkInId;
    }

    public void setSignDate(Date signDate)
    {
        this.signDate = signDate;
    }

    public Date getSignDate()
    {
        return signDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("contractNo", getContractNo())
            .append("elderId", getElderId())
            .append("checkInId", getCheckInId())
            .append("signDate", getSignDate())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("content", getContent())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
