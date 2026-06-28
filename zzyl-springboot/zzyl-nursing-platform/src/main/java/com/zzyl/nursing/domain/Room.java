package com.zzyl.nursing.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zzyl.common.annotation.Excel;
import com.zzyl.common.core.domain.BaseEntity;

/**
 * 房间对象 room
 *
 * @author ruoyi
 * @date 2026-06-25
 */
public class Room extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 所属楼栋ID */
    @Excel(name = "所属楼栋ID")
    private Long floorId;

    /** 房间号 */
    @Excel(name = "房间号")
    private String roomNumber;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    private Integer sortOrder;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setFloorId(Long floorId)
    {
        this.floorId = floorId;
    }

    public Long getFloorId()
    {
        return floorId;
    }

    public void setRoomNumber(String roomNumber)
    {
        this.roomNumber = roomNumber;
    }

    public String getRoomNumber()
    {
        return roomNumber;
    }

    public void setSortOrder(Integer sortOrder)
    {
        this.sortOrder = sortOrder;
    }

    public Integer getSortOrder()
    {
        return sortOrder;
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
            .append("floorId", getFloorId())
            .append("roomNumber", getRoomNumber())
            .append("sortOrder", getSortOrder())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
