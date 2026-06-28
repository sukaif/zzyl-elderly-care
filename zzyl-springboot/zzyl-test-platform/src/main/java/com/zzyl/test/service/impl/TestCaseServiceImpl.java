package com.zzyl.test.service.impl;

import com.zzyl.common.utils.StringUtils;
import com.zzyl.test.domain.TestCase;
import com.zzyl.test.mapper.TestCaseMapper;
import com.zzyl.test.service.ITestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 测试用例Service业务层处理
 */
@Service
public class TestCaseServiceImpl implements ITestCaseService {

    @Autowired
    private TestCaseMapper testCaseMapper;

    @Override
    public List<TestCase> selectTestCaseList(TestCase testCase) {
        return testCaseMapper.selectTestCaseList(testCase);
    }

    @Override
    public TestCase selectTestCaseById(Long id) {
        return testCaseMapper.selectTestCaseById(id);
    }

    @Override
    public int insertTestCase(TestCase testCase) {
        // 自动生成用例编号
        if (StringUtils.isEmpty(testCase.getCaseNo())) {
            testCase.setCaseNo("TC-" + System.currentTimeMillis());
        }
        return testCaseMapper.insertTestCase(testCase);
    }

    @Override
    public int updateTestCase(TestCase testCase) {
        return testCaseMapper.updateTestCase(testCase);
    }

    @Override
    public int deleteTestCaseByIds(Long[] ids) {
        return testCaseMapper.deleteTestCaseByIds(ids);
    }

    @Override
    public TestDashboardVO getDashboardData() {
        TestDashboardVO vo = new TestDashboardVO();
        List<TestCase> allCases = testCaseMapper.selectTestCaseList(new TestCase());
        if (allCases == null || allCases.isEmpty()) {
            return vo;
        }

        vo.setTotal(allCases.size());
        for (TestCase tc : allCases) {
            String status = tc.getStatus();
            if ("已通过".equals(status) || "通过".equals(status)) {
                vo.setPassed(vo.getPassed() + 1);
            } else if ("失败".equals(status)) {
                vo.setFailed(vo.getFailed() + 1);
            } else if ("阻塞".equals(status)) {
                vo.setBlocked(vo.getBlocked() + 1);
            } else {
                vo.setNotRun(vo.getNotRun() + 1);
            }
        }
        if (vo.getTotal() > 0) {
            vo.setPassRate((double) vo.getPassed() / vo.getTotal() * 100);
        }
        return vo;
    }

    @Override
    public String importTestCase(List<TestCase> testCaseList, boolean isUpdateSupport, String operName) {
        if (testCaseList == null || testCaseList.isEmpty()) {
            return "导入数据为空";
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (TestCase testCase : testCaseList) {
            try {
                insertTestCase(testCase);
                successNum++;
            } catch (Exception e) {
                failureNum++;
                failureMsg.append("<br/>").append(failureNum).append("、用例 ").append(testCase.getCaseName()).append(" 导入失败：").append(e.getMessage());
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            return failureMsg.toString();
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条");
            return successMsg.toString();
        }
    }
}
