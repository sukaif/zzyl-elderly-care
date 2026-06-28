package com.zzyl.nursing.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zzyl.common.annotation.Excel;
import com.zzyl.common.core.domain.BaseEntity;

/**
 * 床位对象 bed
 *
 * @author ruoyi
 * @date 2026-06-25
 */
public class Bed extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 所属房间ID */
    @Excel(name = "所属房间ID")
    private Long roomId;

    /** 床位号 */
    @Excel(name = "床位号")
    private String bedNumber;

    /** 状态（0空闲 1已入住 2维修） */
    @Excel(name = "状态", readConverterExp = "0=空闲,1=已入住,2=维修")
    private String status;

    /** 床位价格/月 */
    @Excel(name = "床位价格/月")
    private BigDecimal price;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    private Integer sortOrder;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setRoomId(Long roomId)
    {
        this.roomId = roomId;
    }

    public Long getRoomId()
    {
        return roomId;
    }

    public void setBedNumber(String bedNumber)
    {
        this.bedNumber = bedNumber;
    }

    public String getBedNumber()
    {
        return bedNumber;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setSortOrder(Integer sortOrder)
    {
        this.sortOrder = sortOrder;
    }

    public Integer getSortOrder()
    {
        return sortOrder;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("roomId", getRoomId())
            .append("bedNumber", getBedNumber())
            .append("status", getStatus())
            .append("price", getPrice())
            .append("sortOrder", getSortOrder())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
