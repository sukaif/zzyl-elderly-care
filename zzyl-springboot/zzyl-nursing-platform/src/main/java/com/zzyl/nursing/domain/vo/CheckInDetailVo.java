package com.zzyl.nursing.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zzyl.nursing.domain.Contract;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 入住详情VO
 * 用于查询入住详情的返回数据
 *
 * @author ruoyi
 * @date 2026-06-25
 */
public class CheckInDetailVo {

    /** ===== 入住信息 ===== */
    private Long id;

    /** 入住日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date checkInDate;

    /** 状态（0未入住 1已入住 2已退住） */
    private String status;

    /** ===== 老人信息 ===== */
    private Long elderId;

    private String elderName;

    private Integer age;

    private String gender;

    private String idCardNo;

    private String phone;

    private String address;

    private String image;

    /** 家属信息（JSON字符串） */
    private String familyInfo;

    /** ===== 护理等级 ===== */
    private Long nursingLevelId;

    private String nursingLevelName;

    /** ===== 床位信息 ===== */
    private Long bedId;

    private String bedNumber;

    private String roomNumber;

    private String floorName;

    private BigDecimal bedPrice;

    private BigDecimal nursingFee;

    private BigDecimal totalCost;

    private BigDecimal prePay;

    /** ===== 合同信息 ===== */
    private Contract contract;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFamilyInfo() {
        return familyInfo;
    }

    public void setFamilyInfo(String familyInfo) {
        this.familyInfo = familyInfo;
    }

    public Long getNursingLevelId() {
        return nursingLevelId;
    }

    public void setNursingLevelId(Long nursingLevelId) {
        this.nursingLevelId = nursingLevelId;
    }

    public String getNursingLevelName() {
        return nursingLevelName;
    }

    public void setNursingLevelName(String nursingLevelName) {
        this.nursingLevelName = nursingLevelName;
    }

    public Long getBedId() {
        return bedId;
    }

    public void setBedId(Long bedId) {
        this.bedId = bedId;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public BigDecimal getBedPrice() {
        return bedPrice;
    }

    public void setBedPrice(BigDecimal bedPrice) {
        this.bedPrice = bedPrice;
    }

    public BigDecimal getNursingFee() {
        return nursingFee;
    }

    public void setNursingFee(BigDecimal nursingFee) {
        this.nursingFee = nursingFee;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getPrePay() {
        return prePay;
    }

    public void setPrePay(BigDecimal prePay) {
        this.prePay = prePay;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
