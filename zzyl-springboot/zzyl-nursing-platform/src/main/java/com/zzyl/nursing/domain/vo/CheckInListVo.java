package com.zzyl.nursing.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 * 入住列表VO
 * 用于分页查询入住列表的返回数据
 *
 * @author ruoyi
 * @date 2026-06-25
 */
public class CheckInListVo {

    /** 入住ID */
    private Long id;

    /** 老人ID */
    private Long elderId;

    /** 老人姓名 */
    private String elderName;

    /** 年龄 */
    private Integer age;

    /** 性别 */
    private String gender;

    /** 身份证号 */
    private String idCardNo;

    /** 联系电话 */
    private String phone;

    /** 护理等级名称 */
    private String nursingLevelName;

    /** 入住状态 */
    private String status;

    /** 入住日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date checkInDate;

    /** 床位号 */
    private String bedNumber;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getElderId() {
        return elderId;
    }

    public void setElderId(Long elderId) {
        this.elderId = elderId;
    }

    public String getElderName() {
        return elderName;
    }

    public void setElderName(String elderName) {
        this.elderName = elderName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNursingLevelName() {
        return nursingLevelName;
    }

    public void setNursingLevelName(String nursingLevelName) {
        this.nursingLevelName = nursingLevelName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
