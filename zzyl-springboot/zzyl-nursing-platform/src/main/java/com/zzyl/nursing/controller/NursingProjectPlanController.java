package com.zzyl.nursing.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zzyl.common.annotation.Log;
import com.zzyl.common.core.controller.BaseController;
import com.zzyl.common.core.domain.AjaxResult;
import com.zzyl.common.enums.BusinessType;
import com.zzyl.nursing.domain.NursingProjectPlan;
import com.zzyl.nursing.service.INursingProjectPlanService;
import com.zzyl.common.utils.poi.ExcelUtil;
import com.zzyl.common.core.page.TableDataInfo;

/**
 * 护理计划和项目关联Controller
 * 
 * @author ruoyi
 * @date 2026-06-23
 */
@RestController
@RequestMapping("/zzyl/projectPlan")
public class NursingProjectPlanController extends BaseController
{
    @Autowired
    private INursingProjectPlanService nursingProjectPlanService;

    /**
     * 查询护理计划和项目关联列表
     */
    @PreAuthorize("@ss.hasPermi('zzyl:projectPlan:list')")
    @GetMapping("/list")
    public TableDataInfo list(NursingProjectPlan nursingProjectPlan)
    {
        startPage();
        List<NursingProjectPlan> list = nursingProjectPlanService.selectNursingProjectPlanList(nursingProjectPlan);
        return getDataTable(list);
    }

    /**
     * 导出护理计划和项目关联列表
     */
    @PreAuthorize("@ss.hasPermi('zzyl:projectPlan:export')")
    @Log(title = "护理计划和项目关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, NursingProjectPlan nursingProjectPlan)
    {
        List<NursingProjectPlan> list = nursingProjectPlanService.selectNursingProjectPlanList(nursingProjectPlan);
        ExcelUtil<NursingProjectPlan> util = new ExcelUtil<NursingProjectPlan>(NursingProjectPlan.class);
        util.exportExcel(response, list, "护理计划和项目关联数据");
    }

    /**
     * 获取护理计划和项目关联详细信息
     */
    @PreAuthorize("@ss.hasPermi('zzyl:projectPlan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(nursingProjectPlanService.selectNursingProjectPlanById(id));
    }

    /**
     * 新增护理计划和项目关联
     */
    @PreAuthorize("@ss.hasPermi('zzyl:projectPlan:add')")
    @Log(title = "护理计划和项目关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NursingProjectPlan nursingProjectPlan)
    {
        return toAjax(nursingProjectPlanService.insertNursingProjectPlan(nursingProjectPlan));
    }

    /**
     * 修改护理计划和项目关联
     */
    @PreAuthorize("@ss.hasPermi('zzyl:projectPlan:edit')")
    @Log(title = "护理计划和项目关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NursingProjectPlan nursingProjectPlan)
    {
        return toAjax(nursingProjectPlanService.updateNursingProjectPlan(nursingProjectPlan));
    }

    /**
     * 删除护理计划和项目关联
     */
    @PreAuthorize("@ss.hasPermi('zzyl:projectPlan:remove')")
    @Log(title = "护理计划和项目关联", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(nursingProjectPlanService.deleteNursingProjectPlanByIds(ids));
    }
}
