package com.zzyl.test.controller;

import com.zzyl.common.annotation.Log;
import com.zzyl.common.core.controller.BaseController;
import com.zzyl.common.core.domain.AjaxResult;
import com.zzyl.common.core.page.TableDataInfo;
import com.zzyl.common.enums.BusinessType;
import com.zzyl.common.utils.poi.ExcelUtil;
import com.zzyl.test.domain.TestCase;
import com.zzyl.test.service.ITestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 测试用例Controller
 */
@RestController
@RequestMapping("/test/case")
public class TestCaseController extends BaseController {

    @Autowired
    private ITestCaseService testCaseService;

    /**
     * 查询测试用例列表
     */
    @PreAuthorize("@ss.hasPermi('test:case:list')")
    @GetMapping("/list")
    public TableDataInfo list(TestCase testCase) {
        startPage();
        List<TestCase> list = testCaseService.selectTestCaseList(testCase);
        return getDataTable(list);
    }

    /**
     * 获取测试用例详情
     */
    @PreAuthorize("@ss.hasPermi('test:case:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(testCaseService.selectTestCaseById(id));
    }

    /**
     * 新增测试用例
     */
    @PreAuthorize("@ss.hasPermi('test:case:add')")
    @Log(title = "测试用例", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TestCase testCase) {
        return toAjax(testCaseService.insertTestCase(testCase));
    }

    /**
     * 修改测试用例
     */
    @PreAuthorize("@ss.hasPermi('test:case:edit')")
    @Log(title = "测试用例", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TestCase testCase) {
        return toAjax(testCaseService.updateTestCase(testCase));
    }

    /**
     * 删除测试用例
     */
    @PreAuthorize("@ss.hasPermi('test:case:remove')")
    @Log(title = "测试用例", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(testCaseService.deleteTestCaseByIds(ids));
    }

    /**
     * 获取测试仪表盘数据
     */
    @PreAuthorize("@ss.hasPermi('test:case:list')")
    @GetMapping("/dashboard")
    public AjaxResult dashboard() {
        return success(testCaseService.getDashboardData());
    }

    /**
     * 导出测试用例
     */
    @PreAuthorize("@ss.hasPermi('test:case:export')")
    @Log(title = "测试用例", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TestCase testCase) {
        List<TestCase> list = testCaseService.selectTestCaseList(testCase);
        ExcelUtil<TestCase> util = new ExcelUtil<>(TestCase.class);
        util.exportExcel(response, list, "测试用例数据");
    }

    /**
     * 导入测试用例
     */
    @PreAuthorize("@ss.hasPermi('test:case:import')")
    @Log(title = "测试用例", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<TestCase> util = new ExcelUtil<>(TestCase.class);
        List<TestCase> testCaseList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = testCaseService.importTestCase(testCaseList, updateSupport, operName);
        return success(message);
    }

    /**
     * 下载导入模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<TestCase> util = new ExcelUtil<>(TestCase.class);
        util.importTemplateExcel(response, "测试用例数据");
    }
}
