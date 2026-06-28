package com.zzyl.nursing.domain;

import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zzyl.common.annotation.Excel;
import com.zzyl.common.core.domain.BaseEntity;

/**
 * 入住对象 check_in
 *
 * @author ruoyi
 * @date 2026-06-25
 */
public class CheckIn extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 老人ID */
    @Excel(name = "老人ID")
    private Long elderId;

    /** 护理等级ID */
    @Excel(name = "护理等级ID")
    private Long nursingLevelId;

    /** 入住日期 */
    @Excel(name = "入住日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date checkInDate;

    /** 状态（0未入住 1已入住 2已退住） */
    @Excel(name = "状态", readConverterExp = "0=未入住,1=已入住,2=已退住")
    private String status;

    /** 家属信息（JSON格式） */
    private String familyInfo;

    /** 用于查询：老人姓名（不在数据库） */
    private String elderName;

    /** 用于查询：身份证号（不在数据库） */
    private String idNumber;

    /** 床位ID */
    @Excel(name = "床位ID")
    private Long bedId;

    /** 押金 */
    @Excel(name = "押金")
    private BigDecimal deposit;

    /** 护理费用 */
    @Excel(name = "护理费用")
    private BigDecimal nursingFee;

    /** 床位价格 */
    @Excel(name = "床位价格")
    private BigDecimal bedPrice;

    /** 其他费用 */
    @Excel(name = "其他费用")
    private BigDecimal otherFee;

    /** 医保费用 */
    @Excel(name = "医保费用")
    private BigDecimal medicalFee;

    /** 政府补贴 */
    @Excel(name = "政府补贴")
    private BigDecimal subsidy;

    /** 费用期限 */
    @Excel(name = "费用期限", width = 30, dateFormat = "yyyy-MM-dd")
    private Date feeDate;

    /** 签约日期 */
    @Excel(name = "签约日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date signDate;

    /** 合同名称 */
    @Excel(name = "合同名称")
    private String contractName;

    /** 丙方姓名 */
    @Excel(name = "丙方姓名")
    private String thirdPartyName;

    /** 丙方联系方式 */
    @Excel(name = "丙方联系方式")
    private String thirdPartyPhone;

    /** 入住合同文件 */
    @Excel(name = "入住合同文件")
    private String contractFile;

    /** 合同开始日期 */
    @Excel(name = "合同开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 合同结束日期 */
    @Excel(name = "合同结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setElderId(Long elderId)
    {
        this.elderId = elderId;
    }

    public Long getElderId()
    {
        return elderId;
    }

    public void setNursingLevelId(Long nursingLevelId)
    {
        this.nursingLevelId = nursingLevelId;
    }

    public Long getNursingLevelId()
    {
        return nursingLevelId;
    }

    public void setCheckInDate(Date checkInDate)
    {
        this.checkInDate = checkInDate;
    }

    public Date getCheckInDate()
    {
        return checkInDate;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    public void setFamilyInfo(String familyInfo)
    {
        this.familyInfo = familyInfo;
    }

    public String getFamilyInfo()
    {
        return familyInfo;
    }

    public String getElderName() {
        return elderName;
    }

    public void setElderName(String elderName) {
        this.elderName = elderName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Long getBedId() {
        return bedId;
    }

    public void setBedId(Long bedId) {
        this.bedId = bedId;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public BigDecimal getNursingFee() {
        return nursingFee;
    }

    public void setNursingFee(BigDecimal nursingFee) {
        this.nursingFee = nursingFee;
    }

    public BigDecimal getBedPrice() {
        return bedPrice;
    }

    public void setBedPrice(BigDecimal bedPrice) {
        this.bedPrice = bedPrice;
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

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("elderId", getElderId())
            .append("nursingLevelId", getNursingLevelId())
            .append("checkInDate", getCheckInDate())
            .append("status", getStatus())
            .append("familyInfo", getFamilyInfo())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
