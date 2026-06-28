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
import com.zzyl.common.core.page.TableDataInfo;
import com.zzyl.common.enums.BusinessType;
import com.zzyl.nursing.domain.CheckIn;
import com.zzyl.nursing.domain.dto.CheckInDto;
import com.zzyl.nursing.domain.vo.CheckInDetailVo;
import com.zzyl.nursing.domain.vo.CheckInListVo;
import com.zzyl.nursing.service.ICheckInService;

/**
 * 入住办理Controller
 *
 * @author ruoyi
 * @date 2026-06-25
 */
@RestController
@RequestMapping("/nursing/checkin")
public class CheckInController extends BaseController
{
    @Autowired
    private ICheckInService checkInService;

    /**
     * 1. 分页查询入住列表
     * 参数：elderName（老人姓名）、idNumber（身份证号）、pageNum、pageSize
     */
    @PreAuthorize("@ss.hasPermi('zzyl:checkin:list')")
    @GetMapping("/list")
    public TableDataInfo list(CheckIn checkIn)
    {
        startPage();
        List<CheckInListVo> list = checkInService.selectCheckInListVo(checkIn);
        return getDataTable(list);
    }

    /**
     * 5. 新增入住
     * 提交入住信息后保存数据，涉及多张表（老人表、入住表、入住费用表、合同表、床位表）
     */
    @PreAuthorize("@ss.hasPermi('zzyl:checkin:add')")
    @Log(title = "入住办理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CheckInDto checkInDto)
    {
        return toAjax(checkInService.insertCheckIn(checkInDto));
    }

    /**
     * 6. 查询入住详情
     */
    @PreAuthorize("@ss.hasPermi('zzyl:checkin:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        CheckInDetailVo detail = checkInService.selectCheckInDetailById(id);
        return success(detail);
    }

    /**
     * 修改入住
     */
    @PreAuthorize("@ss.hasPermi('zzyl:checkin:edit')")
    @Log(title = "入住办理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CheckIn checkIn)
    {
        return toAjax(checkInService.updateCheckIn(checkIn));
    }

    /**
     * 删除入住
     */
    @PreAuthorize("@ss.hasPermi('zzyl:checkin:remove')")
    @Log(title = "入住办理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(checkInService.deleteCheckInByIds(ids));
    }
}
