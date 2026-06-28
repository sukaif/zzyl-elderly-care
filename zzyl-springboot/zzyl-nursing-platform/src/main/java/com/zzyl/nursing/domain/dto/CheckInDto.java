package com.zzyl.nursing.domain.dto;

import com.zzyl.nursing.domain.Contract;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 新增入住DTO
 * 接收前端提交的入住登记表单数据
 *
 * @author ruoyi
 * @date 2026-06-25
 */
public class CheckInDto {

    /** ===== 老人信息 ===== */
    /** 老人姓名 */
    private String name;

    /** 年龄 */
    private Integer age;

    /** 性别（0男 1女 2未知） */
    private String gender;

    /** 身份证号 */
    private String idCardNo;

    /** 联系电话 */
    private String phone;

    /** 家庭住址 */
    private String address;

    /** 老人照片 */
    private String image;

    /** 出生日期 */
    private Date birthDate;

    /** 一寸照片 */
    private String photo;

    /** 身份证人像面 */
    private String idCardFront;

    /** 身份证国徽面 */
    private String idCardBack;

    /** ===== 家属信息 ===== */
    /** 家属信息（JSON格式数组） */
    private String familyInfo;

    /** ===== 入住费用 ===== */
    /** 护理等级ID */
    private Long nursingLevelId;

    /** 护理费用 */
    private BigDecimal nursingFee;

    /** 床位ID */
    private Long bedId;

    /** 床位价格 */
    private BigDecimal bedPrice;

    /** 预交费用 */
    private BigDecimal prePay;

    /** 押金 */
    private BigDecimal deposit;

    /** 其他费用 */
    private BigDecimal otherFee;

    /** 医保费用 */
    private BigDecimal medicalFee;

    /** 政府补贴 */
    private BigDecimal subsidy;

    /** ===== 签约信息 ===== */
    /** 入住日期 */
    private Date checkInDate;

    /** 费用期限 */
    private Date feeDate;

    /** 签约日期 */
    private Date signDate;

    /** 合同开始日期 */
    private Date startDate;

    /** 合同结束日期 */
    private Date endDate;

    /** 合同名称 */
    private String contractName;

    /** 丙方姓名 */
    private String thirdPartyName;

    /** 丙方联系方式 */
    private String thirdPartyPhone;

    /** 入住合同文件 */
    private String contractFile;

    /** ===== Getters & Setters ===== */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getIdCardFront() {
        return idCardFront;
    }

    public void setIdCardFront(String idCardFront) {
        this.idCardFront = idCardFront;
    }

    public String getIdCardBack() {
        return idCardBack;
    }

    public void setIdCardBack(String idCardBack) {
        this.idCardBack = idCardBack;
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

    public BigDecimal getNursingFee() {
        return nursingFee;
    }

    public void setNursingFee(BigDecimal nursingFee) {
        this.nursingFee = nursingFee;
    }

    public Long getBedId() {
        return bedId;
    }

    public void setBedId(Long bedId) {
        this.bedId = bedId;
    }

    public BigDecimal getBedPrice() {
        return bedPrice;
    }

    public void setBedPrice(BigDecimal bedPrice) {
        this.bedPrice = bedPrice;
    }

    public BigDecimal getPrePay() {
        return prePay;
    }

    public void setPrePay(BigDecimal prePay) {
        this.prePay = prePay;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public BigDecimal getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(BigDecimal otherFee) {
        this.otherFee = otherFee;
    }

    public BigDecimal getMedicalFee() {
        return medicalFee;
    }

    public void setMedicalFee(BigDecimal medicalFee) {
        this.medicalFee = medicalFee;
    }

    public BigDecimal getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(BigDecimal subsidy) {
        this.subsidy = subsidy;
    }

    public Date getFeeDate() {
        return feeDate;
    }

    public void setFeeDate(Date feeDate) {
        this.feeDate = feeDate;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getThirdPartyName() {
        return thirdPartyName;
    }

    public void setThirdPartyName(String thirdPartyName) {
        this.thirdPartyName = thirdPartyName;
    }

    public String getThirdPartyPhone() {
        return thirdPartyPhone;
    }

    public void setThirdPartyPhone(String thirdPartyPhone) {
        this.thirdPartyPhone = thirdPartyPhone;
    }

    public String getContractFile() {
        return contractFile;
    }

    public void setContractFile(String contractFile) {
        this.contractFile = contractFile;
    }
}
