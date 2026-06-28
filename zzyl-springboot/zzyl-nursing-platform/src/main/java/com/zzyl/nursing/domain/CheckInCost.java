package com.zzyl.nursing.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zzyl.common.annotation.Excel;
import com.zzyl.common.core.domain.BaseEntity;

/**
 * 入住费用对象 check_in_cost
 *
 * @author ruoyi
 * @date 2026-06-25
 */
public class CheckInCost extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 入住ID */
    @Excel(name = "入住ID")
    private Long checkInId;

    /** 床位ID */
    @Excel(name = "床位ID")
    private Long bedId;

    /** 护理费用/月 */
    @Excel(name = "护理费用/月")
    private BigDecimal nursingFee;

    /** 床位价格/月 */
    @Excel(name = "床位价格/月")
    private BigDecimal bedPrice;

    /** 总费用/月 */
    @Excel(name = "总费用/月")
    private BigDecimal totalCost;

    /** 预交费用 */
    @Excel(name = "预交费用")
    private BigDecimal prePay;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setCheckInId(Long checkInId)
    {
        this.checkInId = checkInId;
    }

    public Long getCheckInId()
    {
        return checkInId;
    }

    public void setBedId(Long bedId)
    {
        this.bedId = bedId;
    }

    public Long getBedId()
    {
        return bedId;
    }

    public void setNursingFee(BigDecimal nursingFee)
    {
        this.nursingFee = nursingFee;
    }

    public BigDecimal getNursingFee()
    {
        return nursingFee;
    }

    public void setBedPrice(BigDecimal bedPrice)
    {
        this.bedPrice = bedPrice;
    }

    public BigDecimal getBedPrice()
    {
        return bedPrice;
    }

    public void setTotalCost(BigDecimal totalCost)
    {
        this.totalCost = totalCost;
    }

    public BigDecimal getTotalCost()
    {
        return totalCost;
    }

    public void setPrePay(BigDecimal prePay)
    {
        this.prePay = prePay;
    }

    public BigDecimal getPrePay()
    {
        return prePay;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("checkInId", getCheckInId())
            .append("bedId", getBedId())
            .append("nursingFee", getNursingFee())
            .append("bedPrice", getBedPrice())
            .append("totalCost", getTotalCost())
            .append("prePay", getPrePay())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
