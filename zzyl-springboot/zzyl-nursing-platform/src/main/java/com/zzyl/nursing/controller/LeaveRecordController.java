package com.zzyl.nursing.controller;

import java.util.List;
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
import com.zzyl.common.core.page.TableDataInfo;
import com.zzyl.common.enums.BusinessType;
import com.zzyl.nursing.domain.LeaveRecord;
import com.zzyl.nursing.service.ILeaveRecordService;

/**
 * 请假管理Controller
 *
 * @author ruoyi
 * @date 2026-06-26
 */
@RestController
@RequestMapping("/nursing/leave")
public class LeaveRecordController extends BaseController
{
    @Autowired
    private ILeaveRecordService leaveRecordService;

    /**
     * 分页查询请假列表
     */
    @PreAuthorize("@ss.hasPermi('zzyl:leave:list')")
    @GetMapping("/list")
    public TableDataInfo list(LeaveRecord leaveRecord)
    {
        startPage();
        List<LeaveRecord> list = leaveRecordService.selectLeaveRecordList(leaveRecord);
        return getDataTable(list);
    }

    /**
     * 查询请假详情
     */
    @PreAuthorize("@ss.hasPermi('zzyl:leave:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(leaveRecordService.selectLeaveRecordById(id));
    }

    /**
     * 新增请假
     */
    @PreAuthorize("@ss.hasPermi('zzyl:leave:add')")
    @Log(title = "请假管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LeaveRecord leaveRecord)
    {
        return toAjax(leaveRecordService.insertLeaveRecord(leaveRecord));
    }

    /**
     * 修改请假
     */
    @PreAuthorize("@ss.hasPermi('zzyl:leave:edit')")
    @Log(title = "请假管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LeaveRecord leaveRecord)
    {
        return toAjax(leaveRecordService.updateLeaveRecord(leaveRecord));
    }

    /**
     * 删除请假
     */
    @PreAuthorize("@ss.hasPermi('zzyl:leave:remove')")
    @Log(title = "请假管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(leaveRecordService.deleteLeaveRecordByIds(ids));
    }

    /**
     * 审批请假
     */
    @PreAuthorize("@ss.hasPermi('zzyl:leave:approve')")
    @Log(title = "请假管理", businessType = BusinessType.UPDATE)
    @PutMapping("/approve/{id}")
    public AjaxResult approve(@PathVariable Long id)
    {
        String approver = getUsername();
        return toAjax(leaveRecordService.approveLeave(id, approver));
    }

    /**
     * 销假
     */
    @PreAuthorize("@ss.hasPermi('zzyl:leave:complete')")
    @Log(title = "请假管理", businessType = BusinessType.UPDATE)
    @PutMapping("/complete/{id}")
    public AjaxResult complete(@PathVariable Long id)
    {
        return toAjax(leaveRecordService.completeLeave(id));
    }

    /**
     * 标记超时未归
     */
    @PreAuthorize("@ss.hasPermi('zzyl:leave:edit')")
    @Log(title = "请假管理", businessType = BusinessType.UPDATE)
    @PutMapping("/overdue/{id}")
    public AjaxResult overdue(@PathVariable Long id)
    {
        return toAjax(leaveRecordService.markOverdue(id));
    }
}
