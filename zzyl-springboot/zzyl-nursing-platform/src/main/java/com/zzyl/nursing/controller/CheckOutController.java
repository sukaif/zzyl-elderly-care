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
import com.zzyl.nursing.domain.CheckOut;
import com.zzyl.nursing.service.ICheckOutService;

/**
 * 退住管理Controller
 *
 * @author ruoyi
 * @date 2026-06-26
 */
@RestController
@RequestMapping("/nursing/checkout")
public class CheckOutController extends BaseController
{
    @Autowired
    private ICheckOutService checkOutService;

    /**
     * 分页查询退住列表
     */
    @PreAuthorize("@ss.hasPermi('zzyl:checkout:list')")
    @GetMapping("/list")
    public TableDataInfo list(CheckOut checkOut)
    {
        startPage();
        List<CheckOut> list = checkOutService.selectCheckOutList(checkOut);
        return getDataTable(list);
    }

    /**
     * 查询退住详情
     */
    @PreAuthorize("@ss.hasPermi('zzyl:checkout:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(checkOutService.selectCheckOutById(id));
    }

    /**
     * 新增退住申请
     */
    @PreAuthorize("@ss.hasPermi('zzyl:checkout:add')")
    @Log(title = "退住管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CheckOut checkOut)
    {
        return toAjax(checkOutService.insertCheckOut(checkOut));
    }

    /**
     * 修改退住（结算等）
     */
    @PreAuthorize("@ss.hasPermi('zzyl:checkout:edit')")
    @Log(title = "退住管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CheckOut checkOut)
    {
        return toAjax(checkOutService.updateCheckOut(checkOut));
    }

    /**
     * 删除退住
     */
    @PreAuthorize("@ss.hasPermi('zzyl:checkout:remove')")
    @Log(title = "退住管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(checkOutService.deleteCheckOutByIds(ids));
    }
}
