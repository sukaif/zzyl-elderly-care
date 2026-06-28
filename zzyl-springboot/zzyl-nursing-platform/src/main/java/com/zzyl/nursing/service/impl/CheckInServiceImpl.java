package com.zzyl.nursing.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.zzyl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zzyl.nursing.mapper.CheckInMapper;
import com.zzyl.nursing.mapper.CheckInCostMapper;
import com.zzyl.nursing.mapper.ContractMapper;
import com.zzyl.nursing.mapper.ElderMapper;
import com.zzyl.nursing.mapper.BedMapper;
import com.zzyl.nursing.domain.CheckIn;
import com.zzyl.nursing.domain.CheckInCost;
import com.zzyl.nursing.domain.Contract;
import com.zzyl.nursing.domain.Elder;
import com.zzyl.nursing.domain.dto.CheckInDto;
import com.zzyl.nursing.domain.vo.CheckInDetailVo;
import com.zzyl.nursing.domain.vo.CheckInListVo;
import com.zzyl.nursing.service.ICheckInService;

/**
 * 入住Service业务层处理
 *
 * @author ruoyi
 * @date 2026-06-25
 */
@Service
public class CheckInServiceImpl implements ICheckInService
{
    @Autowired
    private CheckInMapper checkInMapper;

    @Autowired
    private CheckInCostMapper checkInCostMapper;

    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private ElderMapper elderMapper;

    @Autowired
    private BedMapper bedMapper;

    /**
     * 合同编号序列计数器（实际生产环境应使用Redis或数据库序列）
     */
    private static final AtomicInteger seqCounter = new AtomicInteger(0);

    /**
     * 生成合同编号：16位数字
     * 前12位：yyyyMMddHHmmss
     * 后4位：自增序列（从0001开始）
     */
    private synchronized String generateContractNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String prefix = sdf.format(new Date());
        // 每毫秒重置计数器（防止溢出）
        int seq = seqCounter.incrementAndGet();
        if (seq > 9999) {
            seqCounter.set(1);
            seq = 1;
        }
        return prefix + String.format("%04d", seq);
    }

    @Override
    public CheckIn selectCheckInById(Long id)
    {
        return checkInMapper.selectCheckInById(id);
    }

    @Override
    public List<CheckIn> selectCheckInList(CheckIn checkIn)
    {
        return checkInMapper.selectCheckInList(checkIn);
    }

    @Override
    public List<CheckInListVo> selectCheckInListVo(CheckIn checkIn)
    {
        return checkInMapper.selectCheckInListVo(checkIn);
    }

    /**
     * 新增入住（事务管理，涉及5张表）
     * 1. 保存老人信息（elder）
     * 2. 更新床位状态（bed）
     * 3. 保存入住信息（check_in）
     * 4. 保存入住费用（check_in_cost）
     * 5. 保存合同信息（contract）
     */
    @Override
    @Transactional
    public int insertCheckIn(CheckInDto dto)
    {
        // 1. 保存老人信息
        Elder elder = new Elder();
        elder.setName(dto.getName());
        elder.setAge(dto.getAge());
        elder.setGender(dto.getGender());
        elder.setIdCardNo(dto.getIdCardNo());
        elder.setPhone(dto.getPhone());
        elder.setAddress(dto.getAddress());
        elder.setImage(dto.getImage());
        elder.setCreateTime(DateUtils.getNowDate());
        elderMapper.insertElder(elder);

        // 2. 保存入住信息
        CheckIn checkIn = new CheckIn();
        checkIn.setElderId(elder.getId());
        checkIn.setNursingLevelId(dto.getNursingLevelId());
        checkIn.setCheckInDate(dto.getCheckInDate());
        checkIn.setFamilyInfo(dto.getFamilyInfo());
        checkIn.setBedId(dto.getBedId());
        checkIn.setDeposit(dto.getDeposit());
        checkIn.setNursingFee(dto.getNursingFee());
        checkIn.setBedPrice(dto.getBedPrice());
        checkIn.setOtherFee(dto.getOtherFee());
        checkIn.setMedicalFee(dto.getMedicalFee());
        checkIn.setSubsidy(dto.getSubsidy());
        checkIn.setFeeDate(dto.getFeeDate());
        checkIn.setSignDate(dto.getSignDate());
        checkIn.setContractName(dto.getContractName());
        checkIn.setThirdPartyName(dto.getThirdPartyName());
        checkIn.setThirdPartyPhone(dto.getThirdPartyPhone());
        checkIn.setContractFile(dto.getContractFile());
        checkIn.setStartDate(dto.getStartDate());
        checkIn.setEndDate(dto.getEndDate());
        checkIn.setStatus("1"); // 已入住
        checkIn.setCreateTime(DateUtils.getNowDate());
        checkInMapper.insertCheckIn(checkIn);

        // 3. 保存入住费用
        BigDecimal totalCost = BigDecimal.ZERO;
        if (dto.getNursingFee() != null) {
            totalCost = totalCost.add(dto.getNursingFee());
        }
        if (dto.getBedPrice() != null) {
            totalCost = totalCost.add(dto.getBedPrice());
        }

        CheckInCost checkInCost = new CheckInCost();
        checkInCost.setCheckInId(checkIn.getId());
        checkInCost.setBedId(dto.getBedId());
        checkInCost.setNursingFee(dto.getNursingFee());
        checkInCost.setBedPrice(dto.getBedPrice());
        checkInCost.setTotalCost(totalCost);
        checkInCost.setPrePay(dto.getPrePay());
        checkInCost.setCreateTime(DateUtils.getNowDate());
        checkInCostMapper.insertCheckInCost(checkInCost);

        // 4. 更新床位状态为已入住
        if (dto.getBedId() != null) {
            bedMapper.updateBedStatus(dto.getBedId(), "1");
        }

        // 5. 保存合同信息
        Contract contract = new Contract();
        contract.setContractNo(generateContractNo());
        contract.setElderId(elder.getId());
        contract.setCheckInId(checkIn.getId());
        contract.setSignDate(dto.getSignDate() != null ? dto.getSignDate() : new Date());
        contract.setStartDate(dto.getStartDate());
        contract.setEndDate(dto.getEndDate());
        contract.setStatus("1"); // 已签署
        contract.setCreateTime(DateUtils.getNowDate());
        contractMapper.insertContract(contract);

        return 1;
    }

    @Override
    public int updateCheckIn(CheckIn checkIn)
    {
        checkIn.setUpdateTime(DateUtils.getNowDate());
        return checkInMapper.updateCheckIn(checkIn);
    }

    /**
     * 查询入住详情（关联老人、护理等级、床位信息、合同）
     */
    @Override
    public CheckInDetailVo selectCheckInDetailById(Long id)
    {
        CheckIn checkIn = checkInMapper.selectCheckInById(id);
        if (checkIn == null) {
            return null;
        }

        CheckInDetailVo vo = new CheckInDetailVo();

        // 入住基本信息
        vo.setId(checkIn.getId());
        vo.setCheckInDate(checkIn.getCheckInDate());
        vo.setStatus(checkIn.getStatus());
        vo.setFamilyInfo(checkIn.getFamilyInfo());

        // 老人信息
        Elder elder = elderMapper.selectElderById(checkIn.getElderId());
        if (elder != null) {
            vo.setElderId(elder.getId());
            vo.setElderName(elder.getName());
            vo.setAge(elder.getAge());
            vo.setGender(elder.getGender());
            vo.setIdCardNo(elder.getIdCardNo());
            vo.setPhone(elder.getPhone());
            vo.setAddress(elder.getAddress());
            vo.setImage(elder.getImage());
        }

        // 护理等级
        vo.setNursingLevelId(checkIn.getNursingLevelId());

        // 入住费用
        CheckInCost cost = checkInCostMapper.selectCheckInCostByCheckInId(id);
        if (cost != null) {
            vo.setBedId(cost.getBedId());
            vo.setNursingFee(cost.getNursingFee());
            vo.setBedPrice(cost.getBedPrice());
            vo.setTotalCost(cost.getTotalCost());
            vo.setPrePay(cost.getPrePay());
        }

        // 合同信息
        Contract contract = contractMapper.selectContractByCheckInId(id);
        vo.setContract(contract);

        return vo;
    }

    @Override
    public int deleteCheckInByIds(Long[] ids)
    {
        return checkInMapper.deleteCheckInByIds(ids);
    }

    @Override
    public int deleteCheckInById(Long id)
    {
        return checkInMapper.deleteCheckInById(id);
    }
}
