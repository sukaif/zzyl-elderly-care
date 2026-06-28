package com.zzyl.test.domain;

import com.zzyl.common.annotation.Excel;
import com.zzyl.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 测试用例对象 zzyl_test_case
 */
public class TestCase extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 测试用例ID */
    private Long id;

    /** 用例编号 */
    @Excel(name = "用例编号")
    private String caseNo;

    /** 用例名称 */
    @Excel(name = "用例名称")
    private String caseName;

    /** 所属模块 */
    @Excel(name = "所属模块")
    private String module;

    /** 用例类型(功能/接口/性能/安全) */
    @Excel(name = "用例类型")
    private String caseType;

    /** 前置条件 */
    private String precondition;

    /** 测试步骤 */
    @Excel(name = "测试步骤")
    private String testSteps;

    /** 预期结果 */
    @Excel(name = "预期结果")
    private String expectedResult;

    /** 实际结果 */
    @Excel(name = "实际结果")
    private String actualResult;

    /** 用例状态(新建/执行中/通过/失败/阻塞) */
    @Excel(name = "用例状态")
    private String status;

    /** 优先级(P0/P1/P2/P3) */
    @Excel(name = "优先级")
    private String priority;

    /** 执行人 */
    @Excel(name = "执行人")
    private String executor;

    /** 关联需求 */
    private String requirementId;

    /** 关联缺陷 */
    private String bugId;

    /** 备注 */
    private String remark;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCaseNo() { return caseNo; }
    public void setCaseNo(String caseNo) { this.caseNo = caseNo; }

    public String getCaseName() { return caseName; }
    public void setCaseName(String caseName) { this.caseName = caseName; }

    public String getModule() { return module; }
    public void setModule(String module) { this.module = module; }

    public String getCaseType() { return caseType; }
    public void setCaseType(String caseType) { this.caseType = caseType; }

    public String getPrecondition() { return precondition; }
    public void setPrecondition(String precondition) { this.precondition = precondition; }

    public String getTestSteps() { return testSteps; }
    public void setTestSteps(String testSteps) { this.testSteps = testSteps; }

    public String getExpectedResult() { return expectedResult; }
    public void setExpectedResult(String expectedResult) { this.expectedResult = expectedResult; }

    public String getActualResult() { return actualResult; }
    public void setActualResult(String actualResult) { this.actualResult = actualResult; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getExecutor() { return executor; }
    public void setExecutor(String executor) { this.executor = executor; }

    public String getRequirementId() { return requirementId; }
    public void setRequirementId(String requirementId) { this.requirementId = requirementId; }

    public String getBugId() { return bugId; }
    public void setBugId(String bugId) { this.bugId = bugId; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("caseNo", getCaseNo())
                .append("caseName", getCaseName())
                .append("module", getModule())
                .append("caseType", getCaseType())
                .append("status", getStatus())
                .append("priority", getPriority())
                .append("executor", getExecutor())
                .append("createTime", getCreateTime())
                .toString();
    }
}
