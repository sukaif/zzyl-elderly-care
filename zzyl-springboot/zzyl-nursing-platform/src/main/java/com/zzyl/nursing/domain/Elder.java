package com.zzyl.nursing.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zzyl.common.annotation.Excel;
import com.zzyl.common.core.domain.BaseEntity;

/**
 * 老人对象 elder
 *
 * @author ruoyi
 * @date 2026-06-25
 */
public class Elder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 老人姓名 */
    @Excel(name = "老人姓名")
    private String name;

    /** 年龄 */
    @Excel(name = "年龄")
    private Integer age;

    /** 性别（0男 1女 2未知） */
    @Excel(name = "性别", readConverterExp = "0=男,1=女,2=未知")
    private String gender;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String idCardNo;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String phone;

    /** 家庭住址 */
    @Excel(name = "家庭住址")
    private String address;

    /** 老人照片 */
    @Excel(name = "老人照片")
    private String image;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setAge(Integer age)
    {
        this.age = age;
    }

    public Integer getAge()
    {
        return age;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getGender()
    {
        return gender;
    }

    public void setIdCardNo(String idCardNo)
    {
        this.idCardNo = idCardNo;
    }

    public String getIdCardNo()
    {
        return idCardNo;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public String getImage()
    {
        return image;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("age", getAge())
            .append("gender", getGender())
            .append("idCardNo", getIdCardNo())
            .append("phone", getPhone())
            .append("address", getAddress())
            .append("image", getImage())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
