package com.zzyl.nursing.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.zzyl.common.annotation.Log;
import com.zzyl.common.core.controller.BaseController;
import com.zzyl.common.core.domain.AjaxResult;
import com.zzyl.common.core.page.TableDataInfo;
import com.zzyl.common.enums.BusinessType;
import com.zzyl.nursing.domain.HealthAssessment;
import com.zzyl.nursing.service.IHealthAssessmentService;

@RestController
@RequestMapping("/nursing/health")
public class HealthAssessmentController extends BaseController
{
    @Autowired
    private IHealthAssessmentService healthAssessmentService;

    @PreAuthorize("@ss.hasPermi('zzyl:health:list')")
    @GetMapping("/list")
    public TableDataInfo list(HealthAssessment ha)
    {
        startPage();
        List<HealthAssessment> list = healthAssessmentService.selectHealthAssessmentList(ha);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('zzyl:health:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(healthAssessmentService.selectHealthAssessmentById(id));
    }

    @PreAuthorize("@ss.hasPermi('zzyl:health:add')")
    @Log(title = "健康评估", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HealthAssessment ha)
    {
        return toAjax(healthAssessmentService.insertHealthAssessment(ha));
    }

    @PreAuthorize("@ss.hasPermi('zzyl:health:edit')")
    @Log(title = "健康评估", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HealthAssessment ha)
    {
        return toAjax(healthAssessmentService.updateHealthAssessment(ha));
    }

    @PreAuthorize("@ss.hasPermi('zzyl:health:remove')")
    @Log(title = "健康评估", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(healthAssessmentService.deleteHealthAssessmentByIds(ids));
    }
}
