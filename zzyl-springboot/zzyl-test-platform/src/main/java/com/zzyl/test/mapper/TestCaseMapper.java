package com.zzyl.test.mapper;

import com.zzyl.test.domain.TestCase;
import java.util.List;

/**
 * 测试用例Mapper接口
 */
public interface TestCaseMapper {

    /**
     * 根据条件分页查询测试用例列表
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
     * 删除测试用例
     */
    int deleteTestCaseById(Long id);

    /**
     * 批量删除测试用例
     */
    int deleteTestCaseByIds(Long[] ids);

    /**
     * 查询各状态用例数量统计
     */
    List<TestCase> selectTestCaseStatistics();

    /**
     * 查询各模块用例数量
     */
    List<TestCase> selectTestCaseCountByModule();
}
