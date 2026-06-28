package com.zzyl.test.service;

import com.zzyl.test.domain.TestCase;
import java.util.List;

/**
 * 测试用例Service接口
 */
public interface ITestCaseService {

    /**
     * 查询测试用例列表
     */
    List<TestCase> selectTestCaseList(TestCase testCase);

    /**
     * 通过ID查询测试用例
     */
    TestCase selectTestCaseById(Long id);

    /**
     * 新增测试用例
     */
    int insertTestCase(TestCase testCase);

    /**
     * 修改测试用例
     */
    int updateTestCase(TestCase testCase);

    /**
     * 批量删除测试用例
     */
    int deleteTestCaseByIds(Long[] ids);

    /**
     * 获取用例统计仪表盘数据
     */
    TestDashboardVO getDashboardData();

    /**
     * 导入测试用例
     */
    String importTestCase(List<TestCase> testCaseList, boolean isUpdateSupport, String operName);

    /**
     * 仪表盘视图对象
     */
    class TestDashboardVO {
        private long total;           // 总用例数
        private long passed;          // 通过
        private long failed;          // 失败
        private long blocked;         // 阻塞
        private long notRun;          // 未执行
        private double passRate;      // 通过率

        public long getTotal() { return total; }
        public void setTotal(long total) { this.total = total; }
        public long getPassed() { return passed; }
        public void setPassed(long passed) { this.passed = passed; }
        public long getFailed() { return failed; }
        public void setFailed(long failed) { this.failed = failed; }
        public long getBlocked() { return blocked; }
        public void setBlocked(long blocked) { this.blocked = blocked; }
        public long getNotRun() { return notRun; }
        public void setNotRun(long notRun) { this.notRun = notRun; }
        public double getPassRate() { return passRate; }
        public void setPassRate(double passRate) { this.passRate = passRate; }
    }
}
