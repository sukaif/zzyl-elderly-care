package com.zzyl.nursing.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.zzyl.common.annotation.Excel;
import com.zzyl.common.core.domain.BaseEntity;

/**
 * 健康评估对象 health_assessment
 */
public class HealthAssessment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long elderId;
    private String reportTitle;
    private String reportFile;
    private String assessmentResult;
    private Date assessmentDate;
    private String status;

    private BigDecimal healthScore;
    private String suggestAdmission;
    private String recommendNursingLevel;
    private String admissionStatus;
    private Date examinationDate;
    private Date assessmentTime;

    private String elderName;
    private String idCardNo;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getElderId() { return elderId; }
    public void setElderId(Long elderId) { this.elderId = elderId; }
    public String getReportTitle() { return reportTitle; }
    public void setReportTitle(String reportTitle) { this.reportTitle = reportTitle; }
    public String getReportFile() { return reportFile; }
    public void setReportFile(String reportFile) { this.reportFile = reportFile; }
    public String getAssessmentResult() { return assessmentResult; }
    public void setAssessmentResult(String assessmentResult) { this.assessmentResult = assessmentResult; }
    public Date getAssessmentDate() { return assessmentDate; }
    public void setAssessmentDate(Date assessmentDate) { this.assessmentDate = assessmentDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public BigDecimal getHealthScore() { return healthScore; }
    public void setHealthScore(BigDecimal healthScore) { this.healthScore = healthScore; }
    public String getSuggestAdmission() { return suggestAdmission; }
    public void setSuggestAdmission(String suggestAdmission) { this.suggestAdmission = suggestAdmission; }
    public String getRecommendNursingLevel() { return recommendNursingLevel; }
    public void setRecommendNursingLevel(String recommendNursingLevel) { this.recommendNursingLevel = recommendNursingLevel; }
    public String getAdmissionStatus() { return admissionStatus; }
    public void setAdmissionStatus(String admissionStatus) { this.admissionStatus = admissionStatus; }
    public Date getExaminationDate() { return examinationDate; }
    public void setExaminationDate(Date examinationDate) { this.examinationDate = examinationDate; }
    public Date getAssessmentTime() { return assessmentTime; }
    public void setAssessmentTime(Date assessmentTime) { this.assessmentTime = assessmentTime; }
    public String getElderName() { return elderName; }
    public void setElderName(String elderName) { this.elderName = elderName; }
    public String getIdCardNo() { return idCardNo; }
    public void setIdCardNo(String idCardNo) { this.idCardNo = idCardNo; }
}
