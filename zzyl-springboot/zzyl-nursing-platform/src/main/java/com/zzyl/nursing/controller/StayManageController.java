package com.zzyl.nursing.controller;

import com.zzyl.common.core.controller.BaseController;
import com.zzyl.common.core.domain.AjaxResult;
import com.zzyl.common.core.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.zzyl.nursing.domain.CheckIn;
import com.zzyl.nursing.domain.CheckOut;
import com.zzyl.nursing.domain.NoticeRecord;
import com.zzyl.nursing.domain.vo.CheckInListVo;
import com.zzyl.nursing.service.ICheckInService;
import com.zzyl.nursing.service.ICheckOutService;
import com.zzyl.nursing.service.INoticeRecordService;
import com.zzyl.nursing.mapper.CheckInCostMapper;
import com.zzyl.nursing.mapper.CheckInMapper;
import com.zzyl.nursing.domain.CheckInCost;

import java.math.BigDecimal;
import java.util.*;

/**
 * 在住管理Controller
 * 提供房态图、在住列表、流动统计、护理变更等功能
 *
 * @author ruoyi
 * @date 2026-06-26
 */
@RestController
@RequestMapping("/nursing/stay")
public class StayManageController extends BaseController
{
    @Autowired
    private ICheckInService checkInService;

    @Autowired
    private ICheckOutService checkOutService;

    @Autowired
    private INoticeRecordService noticeRecordService;

    @Autowired
    private CheckInMapper checkInMapper;

    @Autowired
    private CheckInCostMapper checkInCostMapper;

    /**
     * 1. 房态图数据 - 按楼栋分组展示床位状态
     * 返回：楼栋 -> 房间 -> 床位（含老人信息）
     */
    @GetMapping("/roomStatus")
    public AjaxResult roomStatus(String status)
    {
        // 使用已有的FloorController中的tree方法
        // 这里返回统计数据供前端展示
        Map<String, Object> result = new HashMap<>();

        // 统计信息
        int totalBeds = 0, occupiedBeds = 0, freeBeds = 0, maintenanceBeds = 0;
        int todayCheckIn = 0, todayCheckOut = 0, onLeave = 0;

        // 从数据库查询统计
        // 总床位
        totalBeds = checkInMapper.countTotalBeds();
        occupiedBeds = checkInMapper.countBedsByStatus("1");
        freeBeds = checkInMapper.countBedsByStatus("0");
        maintenanceBeds = checkInMapper.countBedsByStatus("2");

        // 今日入住/退住
        todayCheckIn = checkInMapper.countTodayCheckIn();
        todayCheckOut = checkInMapper.countTodayCheckOut();

        // 在请假人数
        onLeave = checkInMapper.countOnLeave();

        // 各护理等级人数
        List<Map<String, Object>> levelStats = checkInMapper.countByNursingLevel();

        result.put("totalBeds", totalBeds);
        result.put("occupiedBeds", occupiedBeds);
        result.put("freeBeds", freeBeds);
        result.put("maintenanceBeds", maintenanceBeds);
        result.put("todayCheckIn", todayCheckIn);
        result.put("todayCheckOut", todayCheckOut);
        result.put("onLeave", onLeave);
        result.put("occupancyRate", totalBeds > 0 ? String.format("%.1f", occupiedBeds * 100.0 / totalBeds) : "0");
        result.put("levelStats", levelStats);

        return success(result);
    }

    /**
     * 2. 在住列表 - 已入住的老人列表
     */
    @GetMapping("/residentList")
    public TableDataInfo residentList(CheckIn checkIn)
    {
        checkIn.setStatus("1"); // 只查已入住的
        startPage();
        List<CheckInListVo> list = checkInService.selectCheckInListVo(checkIn);
        return getDataTable(list);
    }

    /**
     * 3. 流动统计
     */
    @GetMapping("/flowStats")
    public AjaxResult flowStats(@RequestParam(value = "startDate", required = false) String startDate,
                                 @RequestParam(value = "endDate", required = false) String endDate)
    {
        Map<String, Object> result = new HashMap<>();

        // 入住人数统计
        int checkInCount = checkInMapper.countCheckInByDateRange(startDate, endDate);
        // 退住人数统计
        int checkOutCount = checkInMapper.countCheckOutByDateRange(startDate, endDate);
        // 请假人次
        int leaveCount = checkInMapper.countLeaveByDateRange(startDate, endDate);

        result.put("checkInCount", checkInCount);
        result.put("checkOutCount", checkOutCount);
        result.put("leaveCount", leaveCount);

        return success(result);
    }

    /**
     * 4. 移入费用计算 - 根据入住费用和退住日期计算结算金额
     */
    @GetMapping("/calcSettlement")
    public AjaxResult calcSettlement(@RequestParam("checkInId") Long checkInId,
                                      @RequestParam("leaveDate") String leaveDate)
    {
        Map<String, Object> result = new HashMap<>();
        CheckInCost cost = checkInCostMapper.selectCheckInCostByCheckInId(checkInId);
        if (cost == null) {
            return error("未找到入住费用信息");
        }

        // 预交费用作为已缴
        BigDecimal totalCharged = cost.getPrePay() != null ? cost.getPrePay() : BigDecimal.ZERO;

        // 计算应扣费用（简化：按月费比例计算）
        BigDecimal monthlyTotal = cost.getTotalCost() != null ? cost.getTotalCost() : BigDecimal.ZERO;

        BigDecimal refundAmount = totalCharged.subtract(monthlyTotal);
        BigDecimal arrears = BigDecimal.ZERO;
        if (refundAmount.compareTo(BigDecimal.ZERO) < 0) {
            arrears = refundAmount.abs();
            refundAmount = BigDecimal.ZERO;
        }

        result.put("totalCharged", totalCharged);    // 已缴
        result.put("totalDeducted", monthlyTotal);    // 应扣
        result.put("refundAmount", refundAmount);      // 应退
        result.put("arrears", arrears);                // 欠款

        return success(result);
    }

    /**
     * 5. 通知告示列表
     */
    @GetMapping("/noticeRecord/list")
    public TableDataInfo noticeRecordList(NoticeRecord noticeRecord)
    {
        startPage();
        List<NoticeRecord> list = noticeRecordService.selectNoticeRecordList(noticeRecord);
        return getDataTable(list);
    }

    /**
     * 6. 新增通知告示
     */
    @PostMapping("/noticeRecord")
    public AjaxResult addNoticeRecord(@RequestBody NoticeRecord noticeRecord)
    {
        return toAjax(noticeRecordService.insertNoticeRecord(noticeRecord));
    }

    /**
     * 7. 删除通知告示
     */
    @DeleteMapping("/noticeRecord/{ids}")
    public AjaxResult delNoticeRecord(@PathVariable Long[] ids)
    {
        return toAjax(noticeRecordService.deleteNoticeRecordByIds(ids));
    }
}
