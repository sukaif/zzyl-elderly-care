package com.zzyl.test.domain;

import com.zzyl.common.annotation.Excel;
import com.zzyl.common.core.domain.BaseEntity;

/**
 * 测试缺陷对象 zzyl_test_bug
 */
public class TestBug extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "缺陷编号")
    private String bugNo;

    @Excel(name = "缺陷标题")
    private String bugTitle;

    @Excel(name = "所属模块")
    private String module;

    @Excel(name = "严重程度")
    private String severity;

    @Excel(name = "优先级")
    private String priority;

    @Excel(name = "缺陷状态")
    private String status;

    private String bugDescription;

    private String reproduceSteps;

    private String expectedResult;

    private String actualResult;

    @Excel(name = "提交人")
    private String reporter;

    @Excel(name = "处理人")
    private String assignee;

    private String resolution;

    @Excel(name = "关联用例")
    private String caseId;

    private String remark;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBugNo() { return bugNo; }
    public void setBugNo(String bugNo) { this.bugNo = bugNo; }

    public String getBugTitle() { return bugTitle; }
    public void setBugTitle(String bugTitle) { this.bugTitle = bugTitle; }

    public String getModule() { return module; }
    public void setModule(String module) { this.module = module; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getBugDescription() { return bugDescription; }
    public void setBugDescription(String bugDescription) { this.bugDescription = bugDescription; }

    public String getReproduceSteps() { return reproduceSteps; }
    public void setReproduceSteps(String reproduceSteps) { this.reproduceSteps = reproduceSteps; }

    public String getExpectedResult() { return expectedResult; }
    public void setExpectedResult(String expectedResult) { this.expectedResult = expectedResult; }

    public String getActualResult() { return actualResult; }
    public void setActualResult(String actualResult) { this.actualResult = actualResult; }

    public String getReporter() { return reporter; }
    public void setReporter(String reporter) { this.reporter = reporter; }

    public String getAssignee() { return assignee; }
    public void setAssignee(String assignee) { this.assignee = assignee; }

    public String getResolution() { return resolution; }
    public void setResolution(String resolution) { this.resolution = resolution; }

    public String getCaseId() { return caseId; }
    public void setCaseId(String caseId) { this.caseId = caseId; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
